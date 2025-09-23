package com.bitcoin.clusterbtc;

import com.bitcoin.clusterbtc.model.Block;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class Batch {

    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
            Service service = new Service();
            // Open connection to DB
            service.openConnection();
            // Get block hash -> Download block -> Parse block
            ArrayList<String> blockHashList = getHashesFromFile();
            for (int i=0; i<130000; i++) {
                Block block = downloadBlock(blockHashList.get(i));
                controller.parseBlocks(block,i);
            }
            // Close connection to DB
            service.closeConnection();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Block downloadBlock(String hash) {
        try {
            URL url = new URL("https://blockchain.info/rawblock/" + hash);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            // Write all the JSON data into a string using a BufferedReader
            String readLine;
            StringBuilder block = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((readLine = reader.readLine()) != null) block.append(readLine);
            return new ObjectMapper().readerFor(Block.class).readValue(String.valueOf(block));
        } catch (IOException e) {
            // If exception is caught -> Restart function
            // e.printStackTrace();
            return downloadBlock(hash);
        }
    }

    private static ArrayList<String> getHashesFromFile() throws IOException {
        ArrayList<String> list = new ArrayList<>();

        File f = new File("src/main/resources/hash.txt");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine;

        System.out.println("Reading file using Buffered Reader...");
        while((readLine = b.readLine()) != null) {
            readLine = readLine.split(",")[1];
            list.add(readLine);
        }
        return list;
    }
}
