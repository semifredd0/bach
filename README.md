### BACH
## Bitcoin Address Clustering based on multiple Heuristics
This project is structured into three main components:<br>
- **Database Generator**: Contains the code to analyze the blockchain and identify address clusters and store all the relationships between addresses within a database.<br>
- **Server API**: Provides the API endpoints for accessing clustering data in the database.<br>
- **Web Client**: A React application that consumes the API to visualize data inside a graph. The [3D Force-Directed Graph](https://github.com/vasturiano/3d-force-graph) web component is used to visualize the cluster graph.<br>
***

## Please cite this work
If you use this software, please cite it. Thank you :)

[Click here to view the paper (Open Access)](https://www.mdpi.com/2078-2489/15/10/589 "View the paper (Open Access)")

- MLA
> Caringella, Michele, et al. "BACH: A Tool for Analyzing Blockchain Transactions Using Address Clustering Heuristics." Information 15.10 (2024): 589.

- APA
> Caringella, M., Violante, F., De Lucci, F., Galantucci, S., & Costantini, M. (2024). BACH: A Tool for Analyzing Blockchain Transactions Using Address Clustering Heuristics. Information, 15(10), 589.

- ISO 690
>CARINGELLA, Michele, et al. BACH: A Tool for Analyzing Blockchain Transactions Using Address Clustering Heuristics. Information, 2024, 15.10: 589.

- BibTex:
> @article{caringella2024bach,
  title={BACH: A Tool for Analyzing Blockchain Transactions Using Address Clustering Heuristics},
  author={Caringella, Michele and Violante, Francesco and De Lucci, Francesco and Galantucci, Stefano and Costantini, Matteo},
  journal={Information},
  volume={15},
  number={10},
  pages={589},
  year={2024},
  publisher={Multidisciplinary Digital Publishing Institute}
}
***

## Homepage
The Homepage presents the tool with a short description, detailing the heuristics used in constructing clusters.<br>
At the bottom of the page, there are examples of discovered clusters.
<p align="center">
<kbd><img src="https://raw.githubusercontent.com/semifredd0/Bach/master/.github/images/gui0.png" width="800"/></kbd>
</p>

## Display cluster graph
In the NavBar you can paste a Bitcoin address to view the cluster to which it belongs.<br>
This page lists all addresses within the same cluster and shows its 3D graph.<br>
The red node identifies the searched address.
<p align="center">
<kbd><img src="https://raw.githubusercontent.com/semifredd0/Bach/master/.github/images/gui1.png" width="800"/></kbd>
</p>

## Display links of an address
By clicking on a node within the graph, you can see all its links to other addresses in the cluster in detail, along with the types of links.
<p align="center">
<kbd><img src="https://raw.githubusercontent.com/semifredd0/Bach/master/.github/images/gui2.png" width="800"/></kbd>
</p>

***
## Author
Matteo Costantini
***
