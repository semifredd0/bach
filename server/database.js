const Pool = require("pg").Pool;

const pool = new Pool({
    user: "matteo",
    password: "password",
    host: "localhost",
    port: "5432",
    database: "cluster_btc"
});

module.exports = pool;