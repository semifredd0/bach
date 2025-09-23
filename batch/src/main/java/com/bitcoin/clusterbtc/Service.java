package com.bitcoin.clusterbtc;

import com.bitcoin.clusterbtc.dto.AddressDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {
    private static Connection con;
    private static PreparedStatement pst;

    public Service() {}

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cluster_btc", "matteo", "password");
    }

    public void closeConnection() throws SQLException {
        con.close();
    }

    public void addAddress(AddressDTO address) {
        // ADDRESS_HASH has a unique constraint
        try {
            pst = con.prepareStatement("insert into address (ADDRESS_HASH,MINER_ADDRESS,ADDRESS_TYPE,CLUSTER_ID) values (?,?,?,?)");
            pst.setString(1,address.getAddress_hash());
            pst.setBoolean(2,address.isMiner_address());
            short type = getType(address.getAddress_hash());
            if(type != -1) pst.setShort(3,type);
            else pst.setNull(3,Types.SMALLINT);
            if(address.getCluster_id() != null) pst.setLong(4,address.getCluster_id());
            else pst.setNull(4,Types.BIGINT);
            pst.executeUpdate();
        } catch (SQLException e) {
            // Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Address already exists!");
        }
    }

    public AddressDTO getAddress(String hash) {
        try {
            pst = con.prepareStatement("select * from ADDRESS where ADDRESS_HASH = ?");
            pst.setString(1,hash);
            ResultSet rs = pst.executeQuery();
            rs.next();
            AddressDTO address = new AddressDTO();
            address.setAddressId(rs.getLong("ADDRESS_ID"));
            address.setAddress_hash(rs.getString("ADDRESS_HASH"));
            address.setMiner_address(rs.getBoolean("MINER_ADDRESS"));
            address.setAddressType(rs.getShort("ADDRESS_TYPE"));
            address.setCluster_id(rs.getLong("CLUSTER_ID"));
            return address;
        } catch (SQLException ex) {
            // Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Cannot find address!");
            return null;
        }
    }

    public List<String> getAddressByCluster(Long clusterID) {
        try {
            pst = con.prepareStatement("select ADDRESS_HASH from ADDRESS where CLUSTER_ID = ?");
            pst.setLong(1,clusterID);
            ResultSet rs = pst.executeQuery();
            List<String> hash_list = new ArrayList<>();
            while(rs.next())
                hash_list.add(rs.getString("ADDRESS_HASH"));
            return hash_list;
        } catch (SQLException ex) {
            // Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Cannot find address!");
            return null;
        }
    }

    public void updateAddressCluster(Long clusterID, String addressHash) {
        try {
            pst = con.prepareStatement("update ADDRESS set CLUSTER_ID = ? where ADDRESS_HASH = ?");
            pst.setLong(1, clusterID);
            pst.setString(2, addressHash);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Cannot update cluster column in address!");
        }
    }

    /** Gli address del vecchio cluster faranno parte del nuovo cluster.
     * Il vecchio cluster sarà vuoto.
     * @param newClusterID New cluster to add.
     * @param oldClusterID Old cluster to substitute.
     */
    public void updateAddressList(Long newClusterID, Long oldClusterID) {
        try {
            pst = con.prepareStatement("update ADDRESS set CLUSTER_ID = ? where CLUSTER_ID = ?");
            pst.setLong(1, newClusterID);
            pst.setLong(2, oldClusterID);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Cannot update cluster column in address!");
        }
    }

    /** Gli address del vecchio cluster faranno parte del nuovo cluster.
     * Il vecchio cluster sarà vuoto.
     * @param newClusterID New cluster to add.
     * @param oldClusterID Old cluster to substitute.
     */
    public void updateSubClusterList(Long newClusterID, Long oldClusterID) {
        try {
            pst = con.prepareStatement("update SUB_CLUSTER set CLUSTER_ID = ? where CLUSTER_ID = ?");
            pst.setLong(1, newClusterID);
            pst.setLong(2, oldClusterID);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Cannot update cluster column in sub_cluster!");
        }
    }

    public void addSubCluster(List<String> addressList, short type) {
        Long clusterId = getClusterId(addressList.get(0));
        for (int i = 0; i < addressList.size() - 1; i++) {
            for (int j = i + 1; j < addressList.size(); j++) {
                Long addressId1 = getAddress(addressList.get(i)).getAddressId();
                Long addressId2 = getAddress(addressList.get(j)).getAddressId();
                try {
                    pst = con.prepareStatement("insert into SUB_CLUSTER (ADDRESS_ID_1,ADDRESS_ID_2,CLUSTER_ID,LINK_TYPE,LINK_VISIBLE_SIZE) values (?,?,?,?,?)");
                    pst.setLong(1, addressId1);
                    pst.setLong(2, addressId2);
                    pst.setLong(3, clusterId);
                    pst.setShort(4, type);
                    // Field used to set visibility of the links in the client app
                    pst.setInt(5, addressList.size());
                    pst.executeUpdate();
                } catch (SQLException e) {
                    // Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Link already exists!");
                }
            }
        }
    }

    // First one is always the change address
    public void addSingleLinkSubCluster(String change_hash, String input_hash, short type) {
        Long clusterId = getClusterId(input_hash);
        Long addressId1 = getAddress(change_hash).getAddressId();
        Long addressId2 = getAddress(input_hash).getAddressId();
        // Verify duplicated links
        if(checkLink(addressId1,addressId2) != null) return;
        if(checkLink(addressId2,addressId1) != null) return;
        try {
            pst = con.prepareStatement("insert into SUB_CLUSTER (ADDRESS_ID_1,ADDRESS_ID_2,CLUSTER_ID,LINK_TYPE) values (?,?,?,?)");
            pst.setLong(1, addressId1);
            pst.setLong(2, addressId2);
            pst.setLong(3, clusterId);
            pst.setShort(4, type);
            pst.executeUpdate();
        } catch (SQLException e) {
            // Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Link already exists!");
        }
    }

    // Return null if link doesn't exist, the addressId otherwise
    private Long checkLink(Long id1, Long id2) {
        try {
            pst = con.prepareStatement("select * from SUB_CLUSTER where ADDRESS_ID_1 = ? and ADDRESS_ID_2 = ?");
            pst.setLong(1,id1);
            pst.setLong(2,id2);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getLong("ADDRESS_ID_1");
        } catch (SQLException ex) {
            // Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Cannot find link!");
            return null;
        }
    }

    private Long getClusterId(String hash) {
        try {
            pst = con.prepareStatement("select CLUSTER_ID from ADDRESS where ADDRESS_HASH = ?");
            pst.setString(1,hash);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getLong("CLUSTER_ID");
        } catch (SQLException ex) {
            // Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Cannot find address!");
            return null;
        }
    }

    private short getType(String hash) {
        // Legacy Address (P2PKH)
        if(hash.startsWith("1")) return 0;
        // Pay to Script Hash (P2SH)
        if(hash.startsWith("3")) return 1;
        // Native SegWit (P2WPKH)
        if(hash.startsWith("bc1q")) return 2;
        // Taproot (P2TR)
        if(hash.startsWith("bc1p")) return 3;
        return -1;
    }
}
