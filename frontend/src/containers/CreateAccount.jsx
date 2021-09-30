import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import TopBalls from "../assets/img/TopBalls.svg";
import "../assets/styles/containers/create.scss";
import Header from "../components/Header";

const CreateAccount = ({}) => {
  const [userID, setUserID] = useState("");
  const [nit, setNit] = useState("");
  const [userType, setUserType] = useState("natural");
  const [accountType, setAccountType] = useState("saving");
  const [error, setError] = useState(false);
  const location = useLocation();
  const [created, setCreated] = useState(false);
  const branch = location.state.branch;
  const cashier = location.state.cashier;
  const [accountID, setAccountID] = useState("0");

  const handleCreation = async (e) => {
    try {
      e.preventDefault();
      const response = await fetch(
        `http://localhost:8080/api/account/${accountType}Account/?client_id=${userID}&sucursal_id=${branch}&nit=${nit}`,
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
      
      if (data.account_id === null){
        setError("El cliente no existe")
        setCreated(false);
      }
      else {
        setAccountID(data.account_id);
        setCreated(true);
      }
    } catch (e) {
      setError(e);
      console.error("Ha habido un error", e);
    }
  };

  return (
    <div className="create-account--container">
      <img src={`${TopBalls}`} className="top-balls" alt="" />
      <Header />
      <form action="" onSubmit={handleCreation}>
        <label htmlFor="usrtype">Enter user type</label>
        <select
          name="usrtype"
          type="text"
          value={userType}
          onChange={(e) => setUserType(e.target.value)}
        >
          <option value="natural">Natural</option>
          <option value="enterprise">Empresa</option>
        </select>
        <label htmlFor="usrid">Enter userID</label>
        <input
          name="usrid"
          type="text"
          value={userID}
          onChange={(e) => setUserID(e.target.value)}
        />
        {userType === "enterprise" ? (
          <>
            <label htmlFor="nit">Enter NIT</label>
            <input
              name="nit"
              type="text"
              value={nit}
              onChange={(e) => setNit(e.target.value)}
            />
          </>
        ) : (
          ""
        )}
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
      {created ? (
        <div>
          <h3>La cuenta con ID {accountID} ha sido creada exitosamente</h3>
        </div>
      ) : (
        ""
      )}
      {error ? <h3 className="error">Ha habido un error: {`${error}`}</h3> : ""}
    </div>
  );
};

export default CreateAccount;
