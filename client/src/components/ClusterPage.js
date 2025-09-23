import React, {Fragment, useEffect, useState} from "react";
import AddressList from "./AddressList";
import axios from "axios";
import ClusterGraph from "./ClusterGraph";
import NavBar from "./NavBar";

function ClusterPage(props) {
    const [cluster, setCluster] = useState([]);
    const [subCluster, setSubCluster] = useState([]);
    const [address, setAddress] = useState("");
    const [empty, setEmpty] = useState(false);

    useEffect(() => {
        fetchData1();
        fetchData2();
        setAddress(props.match.params.addressHash);
    },[]);

    const fetchData1 = async () => {
        const address = props.match.params.addressHash;
        const response = await axios.get('http://localhost:5000/', {
            params: {address: address}
        });
        const data = await response.data;
        if(data.length <= 0) setEmpty(true);
        setCluster(data);
    }

    const fetchData2 = async () => {
        const address = props.match.params.addressHash;
        const response = await axios.get('http://localhost:5000/sub/', {
            params: {address: address}
        });
        setSubCluster(await response.data);
    }

    if (empty)
        return <p>Address or cluster not found!</p>;
    if (cluster.length <= 0 || subCluster.length <= 0)
        return <p>Loading...</p>;

    return (
        <div key={props.match.params.id}>
            <NavBar />
            <Fragment>
                <AddressList data={cluster} />
                <ClusterGraph address={address} data1={cluster} data2={subCluster} />
            </Fragment>
        </div>
    );
}

export default ClusterPage;