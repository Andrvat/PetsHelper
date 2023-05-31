import React, {Component} from "react";
import {
    Card,
    Container,
    ListGroup,
    ListGroupItem,
    Row,
} from "react-bootstrap";
import {Link} from "react-router-dom";
import {withRouter} from "../../../utilites/withRouter";
import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

class UsersList extends Component {
    state = {
        subs: undefined,
        usersList: [],
    };

    constructor(props) {
        super(props);

        this.state.subs = this.props.params.subs;
    }

    componentDidMount() {
        this.getData();
    }

    async getData() {
        console.log("subs: " + this.state.subs);

        let url;
        if (this.state.subs === "subscriptions") {
            url = ACTUAL_BACKEND_ADDRESS + "personality/subscriptions/" + localStorage.getItem("username");
        } else if (this.state.subs === "subscribers") {
            url = ACTUAL_BACKEND_ADDRESS + "/personality/subscribers/" + localStorage.getItem("username");
        }

        const response = await fetch(
            url,
            {
                method: "GET", // *GET, POST, PUT, DELETE, etc.
                mode: "cors", // no-cors, cors, *same-origin
                // cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
                // credentials: "same-origin", // include, *same-origin, omit
                headers: {
                    "Content-Type": "application/json",
                    // "Content-Type": "application/x-www-form-urlencoded",
                },
                redirect: "follow", // manual, *follow, error
                referrer: "no-referrer", // no-referrer, *client
            }
        );

        if (!response.ok) throw new Error(response.status);

        const data = await response.json();

        this.setState({usersList: data});
        console.log(data);

    }

    render() {

        return (
            <div className="App">
                <Container className="p-4">
                    {this.state.usersList.length === 0
                        ? <Card style={{
                            width: "100",
                            height: "4rem",
                            marginTop: "5rem",
                            justifyContent: "center",
                            color: "#626262",
                            fontWeight: "bold",
                            fontFamily: "sans-serif"
                        }}>{this.state.subs === "subscribers" ? "Нет подписчиков" : "Нет подписок"}</Card>
                        : <Row>
                            {this.state.usersList.map((user) => (
                               <div>
                                   {/*//         id: undefined,*/}
                                   {/*//         username: "",*/}
                                   {/*//         firstName: "",*/}
                                   {/*//         lastName: "",*/}
                                   {/*//         city: "",*/}
                                   {/*//         rating: 0,*/}
                                   {/*//     }*/}
                               </div>

                                // <Card
                                //     key={user.id}
                                //     style={{
                                //         width: "18rem",
                                //         marginTop: "5rem",
                                //         marginInline: "15px",
                                //     }}
                                // >
                                //     <Card.Img
                                //         variant="top"
                                //         src={"/images/Beautiful-bird.jpg"}
                                //         style={{marginTop: "11px"}}
                                //     />
                                //     <Card.Body>
                                //         <Link to={"/post/id/" + user.id} style={{textDecoration: "none"}}>
                                //             <Card.Title style={{color: "#383838", fontStyle: "italic"}}>
                                //                 {user.postHeader}
                                //             </Card.Title>
                                //         </Link>
                                //         <Card.Text style={{textAlign: "justify"}}>{user.description}</Card.Text>
                                //     </Card.Body>
                                //     <ListGroup className="list-group-flush">
                                //         <Link to={"/user/" + user.authorUsername} style={{textDecoration: "none"}}>
                                //             <ListGroupItem href={"/user/" + user.authorUsername}
                                //                            style={{color: "#383838", fontWeight: "bold"}}>
                                //                 {"Автор: @" + user.authorUsername}
                                //             </ListGroupItem>
                                //         </Link>
                                //         <ListGroupItem>{"Город: " + user.city}</ListGroupItem>
                                //         <ListGroupItem>{"От: " + user.date}</ListGroupItem>
                                //     </ListGroup>
                                // </Card>
                            ))}
                        </Row>
                    }
                </Container>
            </div>
        );
    }
}

export default withRouter(UsersList);