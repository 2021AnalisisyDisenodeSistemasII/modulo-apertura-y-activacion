import React from "react";
import "../assets/styles/components/action.scss";
import { Link, useHistory } from "react-router-dom";

const Action = ({ name, icon, cashier, branch }) => {
  const history = useHistory();

  const handleclick = () => {
    switch (name) {
      case "Deposit":
        // history.push("/deposit");
        break;

      case "Withdrawal":
        // history.push("/withdrawal");

        break;

      case "Activate account":
        history.push("/activate-account");
        break;

      case "Create account":
        history.push({
          pathname: "/create-account",
          state: { branch, cashier },
        });
        break;

      default:
        break;
    }
  };

  return (
    <div
      onClick={handleclick}
      className={`action ${name.replace(" ", "-").toLowerCase()}`}
    >
      <img src={`${icon}`} alt="" />
      <h3>{name}</h3>
    </div>
  );
};

export default Action;
