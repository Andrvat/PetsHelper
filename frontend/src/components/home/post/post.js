import React, {Component} from 'react'
import {Button, ButtonGroup, Card, CardImg, Col, Container, Row} from "react-bootstrap";
import cardImg from "../../../images/user_photo.jpg";
import PostImgCarousel from "./post_img_carousel";
import CommentsApp from "../comments/comments_app";
import {withRouter} from "../../../utilites/withRouter";
import {Link, useNavigate} from "react-router-dom";
import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

class Post extends Component {

    state = {
        postId: undefined,
        postHeader: undefined,
        actuality: undefined,
        postDescription: undefined,
        authorUsername: undefined,
        email: "не указана",
        phoneNumber: "не указан",
        sectionName: undefined,
        categoryName: undefined,
        city: undefined,
        date: undefined,
        tags: "",
        viewsNumber: 0,
        isOwner: true
    };

    constructor(props) {
        super(props);
        this.state.postId = this.props.params.postId;
        this.publishedPostHandler = this.publishedPostHandler.bind(this);
        this.deletedPostHandler = this.deletedPostHandler.bind(this);
    }

    componentDidMount() {
        this.getData();
    }

    async getData() {
        console.log("postId: " + this.state.postId);

        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "post/" + this.state.postId, {
                method: "GET",
                mode: "cors",
                headers: {
                    "Content-Type": "application/json",
                },
                redirect: "follow",
                referrer: "no-referrer",
            }
        );

        if (!response.ok) throw new Error(response.status);

        const data = await response.json();

        this.setState({postId: data.id});
        this.setState({postHeader: data.postHeader});
        this.setState({actuality: data.actuality});
        this.setState({postDescription: data.description});
        this.setState({authorUsername: data.authorUsername});
        // this.setState({email: data.email});
        // this.setState({phoneNumber: data.phoneNumber});
        this.setState({sectionName: data.sectionName});
        this.setState({categoryName: data.categoryName});
        this.setState({city: data.city});
        this.setState({date: data.date});
        this.setState({tags: data.tags});
        this.setState({viewsNumber: data.viewsNumber});
        // this.setState({isOwner: data.isOwner});
    }

    async publishedPostHandler() {
            const response = await fetch(ACTUAL_BACKEND_ADDRESS + "post/" + this.state.postId + "/moderated", {
                method: "POST", // *GET, POST, PUT, DELETE, etc.
                mode: "cors", // no-cors, cors, *same-origin
// cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
// credentials: "same-origin", // include, *same-origin, omit
                headers: {
                    "Content-Type": "application/json",
// "Content-Type": "application/x-www-form-urlencoded",
                },
                redirect: "follow", // manual, *follow, error
                referrer: "no-referrer", // no-referrer, *client
                body: JSON.stringify(this.state.postId), // body data type must match "Content-Type" header
            });

            if (!response.ok) throw new Error(response.status);

            const data = await response.json();
            if (data.status) {
                console.log("data status", data.status);
            } else {
                alert("Wrong email or password. Try again");
            }
        }

    async deletedPostHandler() {
        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "post/" + this.state.postId + "/delete", {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            mode: "cors", // no-cors, cors, *same-origin
// cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
// credentials: "same-origin", // include, *same-origin, omit
            headers: {
                "Content-Type": "application/json",
// "Content-Type": "application/x-www-form-urlencoded",
            },
            redirect: "follow", // manual, *follow, error
            referrer: "no-referrer", // no-referrer, *client
            body: JSON.stringify(this.state.postId), // body data type must match "Content-Type" header
        });

        if (!response.ok) throw new Error(response.status);

        const data = await response.json();
        if (data.status) {
            console.log("data status", data.status);
        } else {
            alert("Wrong email or password. Try again");
        }
    }

    render() {
        return (
            <Container>
                <Card style={{width: 'auto', marginTop: '7rem'}}>
                    <Row className='no-gutters'>
                        <Col md={5} lg={5}>
                            <PostImgCarousel/>
                            <div style={{
                                marginTop: '1rem',
                                marginBottom: '1rem',
                                marginLeft: '2rem',
                                color: '#808080'
                            }}>Количество просмотров: {" " + this.state.viewsNumber}
                            </div>

                            {(localStorage.getItem("isAdmin")) ?
                                <div>
                                    <Button variant="primary" style={{
                                        width: '94%',
                                        marginTop: '4px',
                                        marginBottom: '1rem',
                                        marginLeft: '2rem',
                                        backgroundColor: '#5cd0c5'
                                    }} onClick={this.publishedPostHandler} href="/post/moderate/all">Опубликовать объявление</Button>
                                    <Button variant="primary" style={{
                                        width: '94%',
                                        marginBottom: '2rem',
                                        marginLeft: '2rem',
                                        backgroundColor: '#5cd0c5'
                                    }} onClick={this.deletedPostHandler} href="/post/moderate/all">Удалить объявление</Button>
                                </div>
                                : (this.state.authorUsername === localStorage.getItem("username") &&
                                (this.state.actuality === "Открыто")) ?
                                <div>
                                    <Button variant="primary" style={{
                                        width: '94%',
                                        marginTop: '4px',
                                        marginLeft: '2rem',
                                        backgroundColor: '#5cd0c5'
                                    }} href={"/user/post/edit/" + this.state.postId}>Редактировать объявление</Button>
                                    <Button variant="primary" style={{
                                        width: '94%',
                                        marginTop: '1rem',
                                        marginLeft: '2rem',
                                        backgroundColor: '#5cd0c5'
                                    }}>Поместить в архив</Button>
                                    <Button variant="primary" style={{
                                        width: '94%',
                                        marginTop: '1rem',
                                        marginBottom: '2rem',
                                        marginLeft: '2rem',
                                        backgroundColor: '#5cd0c5'
                                    }} href="/post/all/all">Удалить объявление</Button>
                                </div>
                                : null}
                        </Col>

                        <Col style={{margin: '45px 20px 20px 11px'}}>
                            <Card.Body>
                                <Card.Title style={{display: 'flex', justifyContent: 'center'}}>
                                    {this.state.postHeader}
                                </Card.Title>
                                <Card.Text style={{
                                    display: 'flex',
                                    justifyContent: 'center',
                                    fontFamily: 'cursive',
                                    color: '#545454'
                                }}>{"(" + this.state.actuality + ")"}</Card.Text>

                                <div className='mx-4'>
                                    <Card.Subtitle style={{marginTop: '20px'}}>Описание:</Card.Subtitle>
                                    <Card.Text style={{
                                        marginTop: '4px',
                                        marginLeft: '2rem',
                                        textAlign: "justify"
                                    }}>{this.state.postDescription}
                                    </Card.Text>

                                    <Card.Subtitle style={{marginTop: '20px'}}>Теги:</Card.Subtitle>
                                    {this.state.tags}

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Автор:</Card.Subtitle>
                                    <Link to={"/user/" + this.state.authorUsername} style={{textDecoration: "none"}}>
                                        <Card.Text style={{marginTop: '4px', marginLeft: '2rem'}}>
                                            {"@" + this.state.authorUsername}
                                        </Card.Text>
                                    </Link>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Контакты:</Card.Subtitle>
                                    <div style={{marginTop: '4px', marginLeft: '2rem'}}>
                                        <Card.Text>{"эл. почта: " + this.state.email}</Card.Text>
                                        <Card.Text>{"номер телефона: " + this.state.phoneNumber}</Card.Text>
                                    </div>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Раздел:</Card.Subtitle>
                                    <Card.Text style={{
                                        marginTop: '4px',
                                        marginLeft: '2rem'
                                    }}>{this.state.sectionName}</Card.Text>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Категория:</Card.Subtitle>
                                    <Card.Text style={{
                                        marginTop: '4px',
                                        marginLeft: '2rem'
                                    }}>{this.state.categoryName}</Card.Text>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Город:</Card.Subtitle>
                                    <Card.Text
                                        style={{marginTop: '4px', marginLeft: '2rem'}}>{this.state.city}</Card.Text>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Дата размещения
                                        объявления:</Card.Subtitle>
                                    <Card.Text
                                        style={{marginTop: '4px', marginLeft: '2rem'}}>{this.state.date}</Card.Text>

                                </div>
                            </Card.Body>
                        </Col>
                    </Row>
                </Card>
                <CommentsApp postId = {this.state.postId}/>
            </Container>
        )
    }
}

export default withRouter(Post);