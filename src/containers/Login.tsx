import React from "react";
import { Link, useHistory } from "react-router-dom";
import "../assets/styles/containers/login.scss";

const Login = () => {
  const history = useHistory();
  const handleClick = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    history.push("/home");
  };

  return (
    <div className="login-container">
      <div className="login-square">
        <h1>Enter Starbank</h1>
        <form action="" onSubmit={handleClick}>
          <input type="text" placeholder="Usuario" required />
          <input type="password" placeholder="Contraseña" required />
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
