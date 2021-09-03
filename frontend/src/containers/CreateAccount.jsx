import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import TopBalls from "../assets/img/TopBalls.svg";
import "../assets/styles/containers/create.scss";
import Header from "../components/Header";

const CreateAccount = ({}) => {
  const [userID, setUserID] = useState("");
  const [accountType, setAccountType] = useState("saving");
  const location = useLocation();
  const branch = location.state.branch;
  const cashier = location.state.cashier;

  const handleCreation = async (e) => {
    try {
      e.preventDefault();
      const response = await fetch(
        `http://localhost:8080/api/account/${accountType}Account/?client_id=${userID}&sucursal_id=${branch}`,
        {
          method: "POST",
          headers: new Headers({
            "Content-Type": "application/json",
          }),
          body: {},
        }
      );
      const data = await response.json();
      console.log(data);
    } catch (e) {
      console.error("Ha habido un error", e);
    }
  };

  return (
    <div className="create-account--container">
      <img src={`${TopBalls}`} className="top-balls" alt="" />
      <Header />
      <form action="" onSubmit={handleCreation}>
        <label htmlFor="usrid">Enter userID</label>
        <input
          name="usrid"
          type="number"
          value={userID}
          onChange={(e) => setUserID(e.target.value)}
        />
        <button>Consultar</button>
        <div>Info</div>
        <label htmlFor="usrid">Tipo de cuenta a crear</label>
        <select
          name=""
          id=""
          onClick={(e) => setAccountType(e.currentTarget.value)}
        >
          <option value="current">Corriente</option>
          <option value="saving">Ahorros</option>
        </select>
        <button type="submit">CREAR</button>
      </form>
    </div>
  );
};

export default CreateAccount;
