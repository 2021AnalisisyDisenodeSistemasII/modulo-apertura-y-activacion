import React from "react";
import { Link } from "react-router-dom";
import LoginIllustration from "../assets/img/login-illustration.svg";
import "../assets/styles/containers/welcome.scss";
const Welcome = () => {
  return (
    <div className="welcome-container">
      <div className="welcome-square">
        <img src={`${LoginIllustration}`} alt="" />
        <h1>The place where your money lives</h1>
        <Link to="/Login" className="welcome-button">
          Log in
        </Link>
        <Link to="/register" className="register-button">
          Register
        </Link>
        <p>
          Don't have an account? <Link to="/register">Create one</Link>
        </p>
      </div>
    </div>
  );
};

export default Welcome;
