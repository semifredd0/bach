package com.bitcoin.clusterbtc;

import com.bitcoin.clusterbtc.dto.AddressDTO;
import com.bitcoin.clusterbtc.dto.ClusterDTO;
import com.bitcoin.clusterbtc.model.Block;
import com.bitcoin.clusterbtc.model.Out;
import com.bitcoin.clusterbtc.model.Tx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class Controller {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Service service = new Service();
    private static Long cluster_id_counter = 1L; // ClusterID counter

    public Controller() throws SQLException {
    }

    public void parseBlocks(Block block, int i) throws IOException {
        // Initialize block object
        List<Tx> list_tx = block.getTx();
        // Logging current block
        System.out.println("Block height: " + i + " - Transaction number: " + block.getTx().size());

        // Coinbase transaction added manually to DB
        // Coinbase transaction can have multiple output
        Tx coinbase = list_tx.get(0);

        // List of effective addresses
        List<String> minerAddressListDuplicates = new ArrayList<>();
        for (int k = 0; k < coinbase.getVout_sz(); k++) {
            if (coinbase.getOut().get(k).getAddr() != null)
                minerAddressListDuplicates.add(coinbase.getOut().get(k).getAddr());
        }
        // Create a list without duplicates
        List<String> minerAddressList = new ArrayList<>(new HashSet<>(minerAddressListDuplicates));

        /* If coinbase has too many output, it could be that
         * a mining pool is distributing the shares directly
         * from the coinbase transaction to the pool members.
         * So do not consider outputs as the same entity.
         * We do not consider coinbase with more than 10 outs.
         * Example: P2Pool and Eligius.
         */
        ClusterDTO clusterDTO = new ClusterDTO();
        for (int k = 0; k < minerAddressList.size(); k++) {
            // Create addressDTO
            AddressDTO minerAddress = new AddressDTO();
            minerAddress.setMiner_address(true);
            minerAddress.setAddress_hash(minerAddressList.get(k));

            // Only one miner or mining pool rewards distribution -> No cluster
            if (minerAddressList.size() == 1 || minerAddressList.size() >= 10)
                service.addAddress(minerAddress);
                // Coinbase cluster
            else if (k == 0) { // First iteration -> Choose the cluster id
                if (service.getAddress(minerAddress.getAddress_hash()) == null) {
                    // New address -> Create new cluster and assign miner address to it
                    clusterDTO.setCluster_id(cluster_id_counter);
                    minerAddress.setCluster_id(cluster_id_counter);
                    // Update ID counter
                    cluster_id_counter++;
                    service.addAddress(minerAddress);
                } else {
                    // Update the actual object with the existing one by address hash
                    minerAddress = service.getAddress(minerAddress.getAddress_hash());
                    if (minerAddress.getCluster_id() != 0) {
                        // Address already exists with a cluster -> Update coinbase cluster ID
                        clusterDTO.setCluster_id(minerAddress.getCluster_id());
                        // Update other addresses in the miner cluster as miners (NO)
                        // service.setMiners(service.getAddressByCluster(clusterDTO.getCluster_id()));
                    } else {
                        // Address exists without a cluster -> Create new cluster and assign miner address to it
                        clusterDTO.setCluster_id(cluster_id_counter);
                        minerAddress.setCluster_id(cluster_id_counter);
                        // Update ID counter
                        cluster_id_counter++;
                        service.updateAddressCluster(clusterDTO.getCluster_id(), minerAddress.getAddress_hash());
                    }
                }
            } else { // Next iterations -> Assign the same cluster ID
                if (service.getAddress(minerAddress.getAddress_hash()) == null) {
                    // New address -> Assign miner address to the coinbase cluster
                    minerAddress.setCluster_id(clusterDTO.getCluster_id());
                    // Save address
                    service.addAddress(minerAddress);
                } else {
                    // Update the actual object with the existing one by address hash
                    minerAddress = service.getAddress(minerAddress.getAddress_hash());
                    if (minerAddress.getCluster_id() != 0) {
                        // Assign all addresses of the coinbase cluster to the miner cluster
                        service.updateAddressList(minerAddress.getCluster_id(), clusterDTO.getCluster_id());
                        // Update clusterID in the graph table
                        service.updateSubClusterList(minerAddress.getCluster_id(), clusterDTO.getCluster_id());
                        // Update coinbase cluster with the miner cluster
                        clusterDTO.setCluster_id(minerAddress.getCluster_id());
                    }
                    // Miner address exists without a cluster -> Assign the coinbase cluster
                    else
                        service.updateAddressCluster(clusterDTO.getCluster_id(), minerAddress.getAddress_hash());
                }
            }
        }

        // Create graph if coinbase heuristic is matched
        if (minerAddressList.size() > 1 && minerAddressList.size() < 10)
            service.addSubCluster(minerAddressList, (short) 0);

        // Add each transaction of the block to DB
        for (int j = 1; j < list_tx.size(); j++) {
            // Logging current transaction
            // LOGGER.log(Level.INFO, "Transaction number: " + j);
            createAddressTransactionDTO(list_tx.get(j));
        }
        // To restart the batch, set clusterIdCounter to the last printed
        // Note: verify no address belongs to that cluster
        System.out.println("Block parsed successfully - ClusterID: " + cluster_id_counter);
    }

    private void createAddressTransactionDTO(Tx transaction) {
        ClusterDTO multi_input_cluster = new ClusterDTO();

        // List of effective addresses
        List<String> inputAddressListDuplicates = new ArrayList<>();
        for(int k=0; k<transaction.getVin_sz(); k++) {
            if (transaction.getInputs().get(k).getPrev_out().getAddr() != null)
                inputAddressListDuplicates.add(transaction.getInputs().get(k).getPrev_out().getAddr());
        }
        // Create a list without duplicates
        List<String> inputAddressList = new ArrayList<>(new HashSet<>(inputAddressListDuplicates));
        // Skip transaction, could not identify input cluster or change address
        if(inputAddressList.size() == 0)    return;

        /** Input txDTO */
        for(int k=0; k<inputAddressList.size(); k++) {
            // Create addressDTO
            AddressDTO tempAddressDTO = new AddressDTO();
            tempAddressDTO.setMiner_address(false);
            tempAddressDTO.setAddress_hash(inputAddressList.get(k));

            // Only one input -> No cluster
            if(inputAddressList.size() == 1)
                service.addAddress(tempAddressDTO);
            // Multi-input cluster
            else if(k == 0) { // First iteration -> Choose the cluster id
                if(service.getAddress(tempAddressDTO.getAddress_hash()) == null) {
                    // New address -> Create new cluster and assign address to it
                    multi_input_cluster.setCluster_id(cluster_id_counter);
                    tempAddressDTO.setCluster_id(cluster_id_counter);
                    // Update ID counter
                    cluster_id_counter++;
                    service.addAddress(tempAddressDTO);
                } else {
                    // Update the actual object with the existing one by address hash
                    tempAddressDTO = service.getAddress(tempAddressDTO.getAddress_hash());
                    if(tempAddressDTO.getCluster_id() != 0) {
                        // Address already exists with a cluster -> Update cluster ID
                        multi_input_cluster.setCluster_id(tempAddressDTO.getCluster_id());
                    } else {
                        // Address exists without a cluster -> Create new cluster and assign address to it
                        multi_input_cluster.setCluster_id(cluster_id_counter);
                        tempAddressDTO.setCluster_id(cluster_id_counter);
                        // Update ID counter
                        cluster_id_counter++;
                        service.updateAddressCluster(multi_input_cluster.getCluster_id(), tempAddressDTO.getAddress_hash());
                    }
                }
            } else { // Next iterations -> Assign the same cluster ID
                if(service.getAddress(tempAddressDTO.getAddress_hash()) == null) {
                    // New address -> Assign address to the coinbase cluster
                    tempAddressDTO.setCluster_id(multi_input_cluster.getCluster_id());
                    // Save address
                    service.addAddress(tempAddressDTO);
                } else {
                    // Update the actual object with the existing one by address hash
                    tempAddressDTO = service.getAddress(tempAddressDTO.getAddress_hash());
                    if(tempAddressDTO.getCluster_id() != 0) {
                        // Assign all addresses of the multi-input cluster to the existing cluster
                        service.updateAddressList(tempAddressDTO.getCluster_id(), multi_input_cluster.getCluster_id());
                        // Update clusterID in the graph table
                        service.updateSubClusterList(tempAddressDTO.getCluster_id(), multi_input_cluster.getCluster_id());
                        // Update the multi-input cluster ID
                        multi_input_cluster.setCluster_id(tempAddressDTO.getCluster_id());
                    }
                    // Input address exists without a cluster -> Assign the multi-input cluster
                    else service.updateAddressCluster(multi_input_cluster.getCluster_id(), tempAddressDTO.getAddress_hash());
                }
            }
        }

        // Create graph if multi-input heuristic is matched
        if(inputAddressList.size() > 1)
            service.addSubCluster(inputAddressList, (short)1);

        // List of effective addresses
        List<String> outputAddressListDuplicates = new ArrayList<>();
        for(int k=0; k<transaction.getVout_sz(); k++) {
            if (transaction.getOut().get(k).getAddr() != null)
                outputAddressListDuplicates.add(transaction.getOut().get(k).getAddr());
        }
        // Create a list without duplicates
        List<String> outputAddressList = new ArrayList<>(new HashSet<>(outputAddressListDuplicates));
        // Skip transaction, could not identify change address
        if(outputAddressList.size() == 0)    return;

        // Hash of the change address
        String change_hash = null;
        // Identify change address
        if(transaction.getVout_sz() >= 2) {
            // ----------- First method
            // Get all addresses in the multi-input cluster
            List<String> hash_list;
            if (multi_input_cluster.getCluster_id() != null)
                hash_list = service.getAddressByCluster(multi_input_cluster.getCluster_id());
            else {
                // Case: multi-input cluster is null because there is only one input
                hash_list = new ArrayList<>();
                if(inputAddressList.size() == 1) // Only one input
                    hash_list.add(inputAddressList.get(0));
            }
            for (String output : outputAddressList) {
                // Verify if one of the output belong to the same cluster
                if (hash_list.contains(output)) {
                    change_hash = output;
                    break;
                }
            }
            /// ----------- Second method, used if first one doesn't work
            if(change_hash == null && inputAddressList.size() > 0) {
                List<String> changes = new ArrayList<>();
                // Save all the new addresses in the list
                for (String output : outputAddressList) {
                    AddressDTO tempAddress = new AddressDTO();
                    tempAddress.setAddress_hash(output);
                    if (service.getAddress(tempAddress.getAddress_hash()) == null)
                        changes.add(tempAddress.getAddress_hash());
                }
                // Verify there's only one new address
                if (changes.size() == 1)
                    change_hash = changes.get(0);
                else if (changes.size() > 1) { // More new addresses in the output
                    // Verify the minimum value in the output addresses
                    // Sort list of outputs by value in ascending
                    List<Out> outList = transaction.getOut();
                    outList.sort(Comparator.comparing(Out::getValue));
                    /* This could be a multi-sig script, so cannot retrive address hash.
                     * In this case we iterate on all the outputs because we want to check
                     *      the value of the output not just if the address hash exists.
                     *      If the output found doesn't have an address, change is null.
                     * Store the minimum value but also the second one, to check that the minimum
                     *      output is the only one minor of all the input.
                     */
                    String temp_change_out1 = outList.get(0).getAddr();
                    Long temp_value_out1 = outList.get(0).getValue();
                    Long temp_value_out2 = outList.get(1).getValue();

                    // Continue only if the change address found have an address hash
                    if(temp_change_out1 != null) {
                        boolean minimum_input_flag = true;
                        boolean only_minimum_flag = true;
                        for (int k=0; k<transaction.getVin_sz(); k++) {
                            // Verify the change address has the minimum value between inputs
                            if (transaction.getInputs().get(k).getPrev_out().getValue() < temp_value_out1) {
                                minimum_input_flag = false;
                                break;
                            }
                            // Verify the change address is the only one having the minimum value between inputs
                            if(transaction.getInputs().get(k).getPrev_out().getValue() > temp_value_out2) {
                                only_minimum_flag = false;
                                break;
                            }
                        }
                        // Change address match the two condition on the values
                        if (minimum_input_flag && only_minimum_flag && changes.contains(temp_change_out1)) {
                            change_hash = temp_change_out1;
                        }
                    }
                }
            }
        }

        /** Output txDTO */
        for (String output : outputAddressList) {
            // Create addressDTO
            AddressDTO tempAddressDTO = new AddressDTO();
            tempAddressDTO.setMiner_address(false);
            tempAddressDTO.setAddress_hash(output);
            // Add address without cluster
            service.addAddress(tempAddressDTO);
        }

        // Add change address and its cluster to the multi-input cluster
        // If input contains the change -> Skip clustering
        if(change_hash != null && !inputAddressList.contains(change_hash)) {
            // Get cluster of the change address
            Long change_address_cluster = service.getAddress(change_hash).getCluster_id();
            // ClusterID could never be zero because counter starts from 1, so 0 = not exists
            if (change_address_cluster != 0 && multi_input_cluster.getCluster_id() != null) {
                // Unify clusters: for each address in the change address cluster -> Set the multi-input cluster ID
                service.updateAddressList(multi_input_cluster.getCluster_id(), change_address_cluster);
                // Update clusterID in the graph table
                service.updateSubClusterList(multi_input_cluster.getCluster_id(), change_address_cluster);
                // Add the change address to the multi-input sub-cluster
                for(String hash : inputAddressList)
                    service.addSingleLinkSubCluster(change_hash, hash, (short) 2);
            } else if (multi_input_cluster.getCluster_id() != null) {
                // Change address cluster is null -> There is a multi-input cluster
                // Add just the change address to the multi-input cluster
                service.updateAddressCluster(multi_input_cluster.getCluster_id(), change_hash);
                // Add the change address to the multi-input sub-cluster
                for(String hash : inputAddressList)
                    service.addSingleLinkSubCluster(change_hash, hash, (short) 2);
            } else {
                // One input and a change address -> Create cluster and sub-cluster
                // Verify if input and change address have a cluster, otherwise create a new one
                Long input_cluster = service.getAddress(inputAddressList.get(0)).getCluster_id();
                if(input_cluster != 0 && change_address_cluster != 0) {
                    // Unify clusters: for each address in the change address cluster -> Set the input cluster ID
                    service.updateAddressList(input_cluster, change_address_cluster);
                    // Update clusterID in the graph table
                    service.updateSubClusterList(input_cluster, change_address_cluster);
                } else if(input_cluster != 0) {
                    // Change address cluster null -> Add the change to the input cluster
                    service.updateAddressCluster(input_cluster, change_hash);
                } else {
                    // Clusters not exists -> Create new one
                    ClusterDTO miniCluster = new ClusterDTO();
                    miniCluster.setCluster_id(cluster_id_counter);
                    // Update cluster ID
                    cluster_id_counter++;
                    service.updateAddressCluster(miniCluster.getCluster_id(), change_hash);
                    service.updateAddressCluster(miniCluster.getCluster_id(), inputAddressList.get(0));
                }
                // Add the change address to the input sub-cluster
                service.addSingleLinkSubCluster(change_hash, inputAddressList.get(0), (short) 2);
            }
        }
    }
}