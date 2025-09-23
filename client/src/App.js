import React from "react";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";

// Components
import Home from "./components/Home";
import ClusterPage from "./components/ClusterPage";
import InfoPage from "./components/InfoPage";

function App() {
    return (
          <Router>
              <div className="main-container">
                  <Switch>
                      <Route exact path="/" component={Home} />
                      <Route exact path="/:addressHash"
                             render={props => <ClusterPage key={props.location.key} {...props} />}
                      />
                      <Route exact path="/info/:addressHash"
                             render={props => <InfoPage key={props.location.key} {...props} />}
                      />
                  </Switch>
              </div>
          </Router>
    );
}

export default App;
