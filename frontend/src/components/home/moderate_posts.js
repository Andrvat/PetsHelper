import React, {Component, useEffect} from "react";
import {
    Card, Col,
    Container,
    ListGroup,
    ListGroupItem,
    Row,
} from "react-bootstrap";
import {Link, useParams, useSearchParams} from "react-router-dom";
import {withRouter} from "../../utilites/withRouter";
import {ACTUAL_BACKEND_ADDRESS} from "../../AppConstants";

class ModeratedPosts extends Component {

    state = {
        postsList: [],
    }


    componentDidMount() {
        this.getData();
    }

    async getData() {
        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "post/moderate/all", {
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
        });

        if (!response.ok) throw new Error(response.status);

        const data = await response.json();

        this.setState({postsList: data});
        console.log("isAdmin = " + localStorage.getItem("isAdmin"));
        console.log("DDDDDAAAAATTTTTAAAAA");
        console.log(data);

    };

    render() {
        return (
            <div className="App">
                <Container className="p-4">
                    {this.state.postsList.length === 0 ? (
                        <Card
                            style={{
                                width: "100",
                                height: "4rem",
                                marginTop: "5rem",
                                justifyContent: "center",
                                color: "#626262",
                                fontWeight: "bold",
                                fontFamily: "sans-serif",
                            }}
                        >
                            Нет запросов на модераторство.
                        </Card>
                    ) : (
                        <Row>
                            {this.state.postsList.map((post) => (
                                <Card
                                    key={post.id}
                                    style={{
                                        width: "18rem",
                                        marginTop: "5rem",
                                        marginInline: "15px",
                                    }}
                                >
                                    <Card.Img
                                        variant="top"
                                        src={"/images/Beautiful-bird.jpg"}
                                        style={{ marginTop: "11px" }}
                                    />
                                    <Card.Body>
                                        <Link
                                            to={"/post/id/" + post.id}
                                            style={{ textDecoration: "none" }}
                                        >
                                            <Card.Title
                                                style={{ color: "#383838", fontStyle: "italic" }}
                                            >
                                                {post.postHeader}
                                            </Card.Title>
                                        </Link>
                                        <Card.Text style={{ textAlign: "justify" }}>
                                            {post.description}
                                        </Card.Text>
                                    </Card.Body>
                                    <ListGroup className="list-group-flush">
                                        <Link
                                            to={"/user/" + post.authorUsername}
                                            style={{ textDecoration: "none" }}
                                        >
                                            <ListGroupItem
                                                href={"/user/" + post.authorUsername}
                                                style={{ color: "#383838", fontWeight: "bold" }}
                                            >
                                                {"Автор: @" + post.authorUsername}
                                            </ListGroupItem>
                                        </Link>
                                        <ListGroupItem>{"Город: " + post.city}</ListGroupItem>
                                        <ListGroupItem>{"От: " + post.date}</ListGroupItem>
                                    </ListGroup>
                                </Card>
                            ))}
                        </Row>
                    )}
                </Container>
            </div>
        );
    };
}

export default withRouter(ModeratedPosts);
