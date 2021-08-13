import React from "react";
import Action from "./Action";
import "../assets/styles/components/actions.scss";
import ActivateIcon from "../assets/img/ActivateIcon.svg";
import DeleteIcon from "../assets/img/DeleteIcon.svg";
import DepositIcon from "../assets/img/DepositIcon.svg";
import WithdrawalIcon from "../assets/img/WithdrawalIcon.svg";

const Actions = ({ branch, cashier }) => {
  return (
    <div className="actions-container">
      <h1>Actions</h1>
      <div className="actions">
        <Action name="Deposit" icon={ActivateIcon} />
        <Action name="Withdrawal" icon={DeleteIcon} />
        <Action
          name="Create account"
          icon={DepositIcon}
          branch={branch}
          cashier={cashier}
        />
        <Action name="Activate account" icon={WithdrawalIcon} />
      </div>
    </div>
  );
};

export default Actions;
