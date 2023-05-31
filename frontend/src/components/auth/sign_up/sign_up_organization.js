import React, {Component} from "react";
import {Link} from "react-router-dom";
import {Navigate} from "react-router";

export default class SignUpOrganization extends Component {
    state = {
        nickname: "",
        organizationName: "",
        email: "",
        password: "",
        isRegistered: false,
    };

    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    handleChange(event) {
        const target = event.target;
        const name = target.name;
        this.setState({ [name]: target.value });
    }

    handleSubmit() {
        console.log("Новый пользователь (организация):", this.state.nickname);

        // const response = await fetch("http://localhost:8080/auth/", {
        //     method: "POST", // *GET, POST, PUT, DELETE, etc.
        //     mode: "cors", // no-cors, cors, *same-origin
        //     // cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        //     // credentials: "same-origin", // include, *same-origin, omit
        //     headers: {
        //       "Content-Type": "application/json",
        //       // "Content-Type": "application/x-www-form-urlencoded",
        //     },
        //     redirect: "follow", // manual, *follow, error
        //     referrer: "no-referrer", // no-referrer, *client
        //     body: JSON.stringify(this.state), // body data type must match "Content-Type" header
        //   });
        // if (!response?.ok) throw new Error(response.status);

        // const data = response.json();
        // if (data.status) {
        //   alert("Welcome to PetsHelper!");
        //   this.jwt = data.jwt;
        //   console.log("jwt", this.jwt);
        // } else {
        //   alert("Wrong email or password. Try again");
        // }

        // TODO: check the OK-response and set isRegistered = true
        this.setState({ isRegistered: true });
    }

    render() {
        let {isRegistered} = this.state;
        return (
            <div>
                {isRegistered && <Navigate to="/auth/sign-in" replace={true}/>}

                <h3>Sign Up Organization</h3>

                <div className="mb-3">
                    <label>User name</label>
                    <input
                        name="nickname"
                        type="text"
                        className="form-control"
                        placeholder="Nickname"
                        onChange={this.handleChange}
                    />
                </div>

                <div className="mb-3">
                    <label>Full organization name</label>
                    <input
                        name="organizationName"
                        type="text"
                        className="form-control"
                        placeholder="Enter organization name"
                        onChange={this.handleChange}
                    />
                </div>

                <div className="mb-3">
                    <label>Email address</label>
                    <input
                        name="email"
                        type="email"
                        className="form-control"
                        placeholder="Enter email"
                        onChange={this.handleChange}
                    />
                </div>

                <div className="mb-3">
                    <label>Password</label>
                    <input
                        name="password"
                        type="password"
                        className="form-control"
                        placeholder="Enter password"
                        onChange={this.handleChange}
                    />
                </div>

                <div className="d-grid">
                    <button type="submit" className="btn btn-primary mb-3 mt-2" onClick={this.handleSubmit}>
                        Sign Up
                    </button>
                </div>

                <p className="forgot-password text-right color:black">
                    Already registered <Link to="/auth/sign-in">sign in?</Link>
                </p>
            </div>
        );
    }
}
