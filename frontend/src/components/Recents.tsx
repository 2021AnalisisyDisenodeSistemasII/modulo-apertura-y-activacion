import React from "react";
import Recent from "./Recent";
import "../assets/styles/components/recents.scss";

const Recents = () => {
  return (
    <div className="recents-container">
      <h1>Recents Actions</h1>
      <div className="recents">
        <Recent type="Deposit" value="30.000" />
        <Recent type="Withdrawal" value="10.000" />
        <Recent type="Created Account" value="✔" />
        <Recent type="Activated Account" value="✔" />
      </div>
    </div>
  );
};

export default Recents;
