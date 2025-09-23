const express = require("express");
const app = express();
const cors = require("cors");
const pool = require("./database");

// Middleware
app.use(cors());
app.use(express.json());

// Route: get all addresses from a cluster
app.get("/", async(req,res) => {
    try {
        const addressHash = req.query.address;
        // Get all addresses in the same cluster of addressHash
        const cluster = await pool.query("select * from address where cluster_id = " +
            "(select cluster_id from address where address_hash = $1)",[addressHash]);
        res.json(cluster.rows);
    } catch(err) {
        console.error(err.message);
    }
});

// Route: get all links of the cluster from an address
app.get("/sub/", async(req,res) => {
    try {
        const addressHash = req.query.address;
        // Get all addresses in the same cluster of addressHash
        const links = await pool.query("select * from sub_cluster where cluster_id = " +
            "(select cluster_id from address where address_hash = $1)",[addressHash]);
        res.json(links.rows);
    } catch(err) {
        console.error(err.message);
    }
});

// Route: get all links of an address
app.get("/info/", async(req,res) => {
    try {
        const addressHash = req.query.address;
        // Get id of the address
        const addressIdRecord = await pool.query("select address_id from address where address_hash = $1",[addressHash]);
        const addressId = addressIdRecord.rows[0].address_id;
        // Get all links of the address from ID
        const links = await pool.query("select * from sub_cluster where address_id_1 = $1 or address_id_2 = $2",[addressId,addressId]);
        // Get all hashes of the the addresses from IDs
        for(let i=0; i<links.rowCount;i++) {
            const tempHash1 = await pool.query("select address_hash from address where address_id = $1",[links.rows[i].address_id_1]);
            const tempHash2 = await pool.query("select address_hash from address where address_id = $1",[links.rows[i].address_id_2]);
            links.rows[i].address_id_1 = tempHash1.rows[0].address_hash;
            links.rows[i].address_id_2 = tempHash2.rows[0].address_hash;
        }
        res.json(links.rows);
    } catch(err) {
        console.error(err.message);
    }
});

app.listen(5000, () => {
   console.log("Server has started on port 5000")
});