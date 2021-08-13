import React from "react";
import Actions from "../components/Actions";
import Recents from "../components/Recents";
import Header from "../components/Header";
import TopBalls from "../assets/img/TopBalls.svg";
import "../assets/styles/containers/home.scss";
import { useLocation } from "react-router-dom";

const Home = () => {
  const location = useLocation();
  const branch = location.state.branch;
  const cashier = location.state.cashier;
  console.log(branch);
  console.log(cashier);

  return (
    <div className="home-container">
      <img src={`${TopBalls}`} className="top-balls" alt="" />
      <Header />
      <Actions cashier={cashier} branch={branch} />
      <Recents />
    </div>
  );
};

export default Home;
