import React from "react";
import "../assets/styles/components/recent.scss";

interface Props {
  type: string;
  value: string;
}

const Recent = ({ type, value }: Props) => {
  return (
    <div className={`recent ${type.replace(" ", "-").toLowerCase()}`}>
      <div className="details">
        <h3>{type}</h3>
        <p className="account">Account #1</p>
        <p className="date">{Date.now()}</p>
      </div>
      <h3 className="value">{value}</h3>
    </div>
  );
};

export default Recent;
