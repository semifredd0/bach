import React, { Fragment, useEffect, useState } from "react";
import '../styles/AddressList.css';
import {Pagination} from 'antd';

function AddressList({data}) {
    const [total,setTotal] = useState("");
    const [page,setPage] = useState(1);
    const [addressPerPage,setAddressPerPage] = useState(11);

    useEffect(() => {
        setTotal(data.length);
    });

    const indexOfLastPage = page * addressPerPage;
    const indexOfFirstPage = indexOfLastPage - addressPerPage;
    const currentAddresses = data.slice(indexOfFirstPage,indexOfLastPage);

    return (
        <Fragment>
            <div className="card1">
                <div className="card-body">
                    <p>Addresses in the cluster: {data.length}</p>
                    <table className="table table-hover">
                        <tbody>
                            {currentAddresses.map((address,index) => (
                                <tr key={index}>
                                    <td>{address.address_hash}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <Pagination onChange={(value) => setPage(value)}
                                pageSize={addressPerPage}
                                total={total}
                                current={page}
                                showSizeChanger={false} />
                </div>
            </div>
        </Fragment>
    );
};

export default AddressList;