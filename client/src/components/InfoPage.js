import React, {Fragment, useEffect, useState} from "react";
import axios from "axios";
import NavBar from "./NavBar";
import AddressLinks from "./AddressLinks";

function InfoPage(props) {
    const [linkSet, setLinkSet] = useState([]);
    const [empty, setEmpty] = useState(false);

    useEffect(() => {
        fetchData1();
    },[]);

    const fetchData1 = async () => {
        const address = props.match.params.addressHash;
        const response = await axios.get('http://localhost:5000/info/', {
            params: {address: address}
        });
        const data = await response.data;
        if(data.length <= 0) setEmpty(true);
        setLinkSet(data);
    }

    if (empty)
        return <p>Address or cluster not found!</p>;
    if (linkSet.length <= 0)
        return <p>Loading...</p>;

    return (
        <div key={props.match.params.id}>
            <NavBar />
            <Fragment>
                <AddressLinks address={props.match.params.addressHash} data={linkSet} />
            </Fragment>
        </div>
    );
}

export default InfoPage;