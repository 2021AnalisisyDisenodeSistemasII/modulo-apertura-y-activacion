import React from "react";
import "../assets/styles/components/header.scss";
import Logo from "../assets/img/Logo.svg";

const Header = () => {
  return (
    <div className="header">
      <img src={`${Logo}`} alt="" />
    </div>
  );
};

export default Header;
