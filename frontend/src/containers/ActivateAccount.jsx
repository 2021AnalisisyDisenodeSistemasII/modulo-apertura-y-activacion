import React, { useState } from "react";
import Header from "../components/Header";
import TopBalls from "../assets/img/TopBalls.svg";
import "../assets/styles/containers/create.scss";

const ActivateAccount = () => {
  const [accountType, setAccountType] = useState("saving");
  const [accountID, setAccountID] = useState("");
  const [created, setCreated] = useState(false);
  const [error, setError] = useState();

  const handleActivation = async (e) => {
    e.preventDefault();
    const response = await fetch(
      `http://localhost:8080/api/account/active/${accountType}Account/?account_id=${accountID}`,
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
    if (data === true) {
      setCreated(true);
      setError(false);
    } else {
      setCreated(false);
      setError(`No se pudo activar la cuenta ${accountID}`);
    }
  };

  return (
    <div className="create-account--container">
      <img src={`${TopBalls}`} className="top-balls" alt="" />
      <Header />
      <form action="" preventdefault="true" onSubmit={handleActivation}>
        <label htmlFor="accountid">Enter accountID</label>
        <input
          name="accountid"
          type="text"
          value={accountID}
          onChange={(e) => {
            setAccountID(e.target.value);
            setCreated(false);
          }}
        />
        <label htmlFor="accounttype">Tipo de cuenta a activar</label>
        <select
          name="accounttype"
          onClick={(e) => setAccountType(e.currentTarget.value)}
        >
          <option value="saving">Ahorros</option>
          <option value="current">Corriente</option>
        </select>
        <button type="submit">Activar cuenta</button>
      </form>
      {created ? (
        <div>
          <h3>La cuenta con ID {accountID} ha sido activada exitosamente</h3>
        </div>
      ) : (
        ""
      )}
      {error ? <h3 className="error">Ha habido un error: {`${error}`}</h3> : ""}
    </div>
  );
};

export default ActivateAccount;
