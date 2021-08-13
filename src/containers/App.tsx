import React from "react";
import Init from "./Welcome";
import Login from "./Login";
import Home from "./Home";
import CreateAccount from "./CreateAccount";
import ActivateAccount from "./ActivateAccount";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import "../assets/styles/global.scss";

const App = () => {
  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Init />
        </Route>
        <Route path="/login">
          <Login />
        </Route>
        <Route path="/register">
          <Init />
        </Route>
        <Route path="/home">
          <Home />
        </Route>
        <Route path="/create-account">
          <CreateAccount />
        </Route>
        <Route path="/activate-account">
          <ActivateAccount />
        </Route>
      </Switch>
    </Router>
  );
};

export default App;
