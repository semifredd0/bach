import React, { Fragment, useEffect, useState } from "react";
import '../styles/AddressLink.css';
import {Pagination} from 'antd';

function AddressLinks({address,data}) {
    const [total,setTotal] = useState("");
    const [page,setPage] = useState(1);
    const [linkPerPage,setLinkPerPage] = useState(10);

    useEffect(() => {
        setTotal(data.length);
    });

    const indexOfLastPage = page * linkPerPage;
    const indexOfFirstPage = indexOfLastPage - linkPerPage;
    const currentLinks = data.slice(indexOfFirstPage,indexOfLastPage);

    return (
        <Fragment>
            <div className="card1" id="card1-info">
                <div className="card-body">
                    <p id="main">Links of address: {data.length}</p>
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th>Address 1</th>
                                <th>Address 2</th>
                                <th>Link</th>
                            </tr>
                        </thead>
                        <tbody>
                            {currentLinks.map((link,index) => (
                                <tr key={index}>
                                    <td className="address-col"
                                        style={{background: link.address_id_1 === address ? "lightgray" : "white"}}>
                                        {link.address_id_1}</td>
                                    <td className="address-col"
                                        style={{background: link.address_id_2 === address ? "lightgray" : "white"}}>
                                        {link.address_id_2}</td>
                                    <td>{link.link_type}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <Pagination onChange={(value) => setPage(value)}
                                pageSize={linkPerPage}
                                total={total}
                                current={page}
                                showSizeChanger={false} />
                </div>
                <div className="card-body">
                    <p id="legend">Link types:<br/>
                    0 -- Coinbase Clustering Heuristic<br/>
                    1 -- Multi-Input Clustering Heuristic<br/>
                    2 --s Change Address Clustering Heuristic<br/><br/>
                        If link type=2<br/>
                        then address 1 is the change address</p>
                </div>
            </div>
        </Fragment>
    );
};

export default AddressLinks;