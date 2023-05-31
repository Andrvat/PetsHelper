import React, { Component } from "react";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";

export default class SignUp extends Component {
  render() {
    return (
      <div>
        <Link to={"./private"}>
          <Button className="btn-link w-100 p-4">Частное лицо</Button>
        </Link>
        <Link to={"./organization"}>
          <Button className="btn-link mt-3 w-100 py-4">Организация</Button>
        </Link>
      </div>
    );
  }
}
