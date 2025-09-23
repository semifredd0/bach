import '../styles/Home.css';
import React from "react";
import NavBar from "./NavBar";

function Home() {
    return(
        <div className="Home">
            <NavBar />
            <div className="title-box">
                <img className="logo" src="logo.png" alt="Logo" />
                <div className="sub-title-box">
                    <p className="title1">BACH</p>
                    <p className="title2">Bitcoin Address Clustering based on multiple Heuristics</p>
                </div>
            </div>
            <p className="description">
                The Bach tool, starting from a bitcoin address, allows to visualize the entire cluster of addresses
                constructed using the following heuristics:
            </p>
            <ul>
                <li><div className="box orange"/>
                    <div className="text-div">Multi-Input Clustering Heuristic</div></li>
                <li><div className="box green"/>
                    <div className="text-div">Coinbase Clustering Heuristic</div></li>
                <li><div className="box cyan"/>
                    <div className="text-div">Change Address Clustering Heuristic</div></li>
            </ul>
            <p className="description">
                In addition, three types of addresses are identified:
            </p>
            <ul>
                <li><div className="box red"/>
                    <div className="text-div">Address searched</div></li>
                <li><div className="box blue"/>
                    <div className="text-div">Non-miner address</div></li>
                <li><div className="box lime"/>
                    <div className="text-div">Miner address</div></li>
            </ul>
            <p className="description">
                The tool, besides indicating all the addresses present in the cluster, allows to visualize the relations
                present between the various addresses in a 3D graph.<br/>
                Moreover, by clicking on a node within the graph, you can view all the list of relations of that address
                with its type of heuristic.<br/>
                Note: the displayed data was collected in the first 150,000 blocks.<br/>
                Here are some examples of clusters found:
            </p>
            <ul>
                <li><a href="http://localhost:3000/12kc4iXPztvatiMtbBNJBZT1LYk4fo82Zo">Cluster 1</a></li>
                <li><a href="http://localhost:3000/1FYNnV2ZVFBXFFrvZhDHFDGfrZyWXLBAEA">Cluster 2</a></li>
                <li><a href="http://localhost:3000/1GQ57FAHzBYHe1NcENCvvp3iG6xAoc9Se5">Cluster 3</a></li>
            </ul>
            <p className="description" />
        </div>
    );
}

export default Home;