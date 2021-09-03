import React, { useState } from "react";
import { Link, useHistory } from "react-router-dom";
import "../assets/styles/containers/login.scss";

const Login = () => {
  const history = useHistory();
  const handleClick = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    history.push({
      pathname: "/home",
      state: { branch, cashier },
    });
  };

  const [branch, setBranch] = useState(1);
  const [cashier, setCashier] = useState("cashier 1");

  return (
    <div className="login-container">
      <div className="login-square">
        <h1>Enter Starbank</h1>
        <form action="" onSubmit={handleClick}>
          <label htmlFor="branch">Branch</label>
          <select name="branch" id="branch" required>
            <option value="1">Medellín</option>
            <option value="2">Barranquilla</option>
            <option value="3">Bogotá</option>
          </select>
          <label htmlFor="cashier">Cashier</label>
          <select name="cashier" id="cashier" required>
            <option value="cashier1">Cashier1</option>
            <option value="cashier2">Cashier2</option>
            <option value="cashier3">Cashier3</option>
          </select>
          <button type="submit" className="login-button">
            Log in
          </button>
          <p>
            Don't have an account? <Link to="/register">Create one</Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default Login;
