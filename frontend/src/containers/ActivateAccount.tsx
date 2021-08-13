import React from "react";
import Header from "../components/Header";
import TopBalls from "../assets/img/TopBalls.svg";

const ActivateAccount = () => {
  return (
    <div className="create-account--container">
      <img src={`${TopBalls}`} className="top-balls" alt="" />
      <Header />
      <form action="">
        <label htmlFor="usrid">Enter userID</label>
        <input name="usrid" type="number" />
        <label htmlFor="Account">Choose an account type:</label>
        <select name="account" id="account">
          <option value="Savings">Savings</option>
          <option value="Current">Current</option>
        </select>
      </form>
    </div>
  );
};

export default ActivateAccount;
