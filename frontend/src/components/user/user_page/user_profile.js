import React, {Component} from 'react'
import {Button, Card, Col, Row} from "react-bootstrap";
import cardImg from "../../../images/user_photo.jpg";
import {Navigate} from "react-router";
import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

export default class UserProfile extends Component {

    let
    constacts = ""

    state = {
        id: undefined,
        username: "",
        firstName: "",
        lastName: "",
        organizationName: undefined,
        email: " - ",
        phoneNumber: " - ",
        city: "",
        postsNumber: 0,
        subscribersNumber: 0,
        subscriptionsNumber: 0,
        gender: "",
        aboutMe: "",
        rating: 0,
        goEdit: false
    }

    constructor(props) {
        console.log("HERE")
        super(props);
        this.handleClick = this.handleClick.bind(this)

        this.state = {isMyProfile: 1, isSubscribed: 0, isOrganization: 0};
    }

    componentDidMount() {
        this.getData();
    }

    async getData() {
        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "personality/username/" + localStorage.getItem("username"), {
            method: "GET",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
            redirect: "follow",
            referrer: "no-referrer",
        });

        if (!response.ok) throw new Error(response.status);

        const data = await response.json();
        console.log("data: " + data);

        this.setState({id: data.id})
        this.setState({username: data.username})
        this.setState({firstName: data.firstName})
        this.setState({lastName: data.lastName})
        this.setState({aboutMe: data.aboutMe})
        this.setState({email: data.email})
        this.setState({phoneNumber: data.phoneNumber})
        this.setState({city: data.city})
        this.setState({postsNumber: data.postsNumber})
        this.setState({subscribersNumber: data.subscribersNumber})
        this.setState({subscriptionsNumber: data.subscriptionsNumber})

        if(this.state.email != null) {
            this.constacts += this.state.email
        }
        if(this.state.phoneNumber != null) {
            this.constacts += " " + this.state.phoneNumber;
        }

    }

    // constructor(props) {
    //     super(props);
    //
    //     this.state = {isMyProfile: 0, isSubscribed: 0, isOrganization: 1};
    //     this.handleClick = this.handleClick.bind(this)
    // }
    //
    handleClick() {
        this.setState({goEdit: true})
    }

    render() {

        let {goEdit} = this.state;

        return (
            <div>
                {goEdit && <Navigate to="edit" replace={true}/>}

                <Card style={{width: 'auto', marginTop: '7rem', height: '43rem'}}>
                    <Row className='no-gutters'>
                        <Col md={5} lg={5}>
                            <Card.Img variant="top" src={cardImg} style={{
                                marginTop: '4rem',
                                marginLeft: '2rem',
                                width: '500px',
                                height: '500px',
                                objectFit: 'cover'
                            }}/>

                            {this.state.isMyProfile === 1
                                ? <Button variant="primary" style={{
                                    width: '500px',
                                    marginTop: '2rem',
                                    marginLeft: '2rem',
                                    backgroundColor: '#5cd0c5'
                                }} onClick={this.handleClick}>Редактировать</Button>
                                // }}>Редактировать</Button>

                                : this.state.isSubscribed === 0
                                    ? <Button variant="primary" style={{
                                        width: '500px',
                                        marginTop: '2rem',
                                        marginLeft: '2rem',
                                        backgroundColor: '#5cd0c5'
                                    }}>Подписаться</Button>

                                    : <Button variant="primary" style={{
                                        width: '500px',
                                        marginTop: '2rem',
                                        marginLeft: '2rem',
                                        backgroundColor: '#68ded3'
                                    }}>Отписаться</Button>
                            }
                        </Col>
                        <Col style={{margin: '45px 20px 20px 11px'}}>
                            <Card.Body>
                                <Card.Title style={{display: 'flex', justifyContent: 'center'}}>
                                    {this.state.firstName + " " + this.state.lastName}
                                </Card.Title>

                                {this.state.isOrganization === 0
                                    ? <Card.Text style={{
                                        display: 'flex',
                                        justifyContent: 'center',
                                        fontFamily: 'cursive',
                                        color: '#545454'
                                    }}>(частное лицо)</Card.Text>
                                    : <Card.Text style={{
                                        display: 'flex',
                                        justifyContent: 'center',
                                        fontFamily: 'cursive',
                                        color: '#545454'
                                    }}>(организация)</Card.Text>
                                }

                                <div className='mx-4'>
                                    <Card.Subtitle style={{marginTop: '20px'}}>Никнейм:</Card.Subtitle>
                                    <Card.Text
                                        style={{marginTop: '4px', marginLeft: '2rem'}}>@{this.state.username}</Card.Text>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Контакты:</Card.Subtitle>
                                    <Card.Text
                                        style={{
                                            marginTop: '4px',
                                            marginLeft: '2rem'
                                        }}>{this.constacts}</Card.Text>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Город:</Card.Subtitle>
                                    <Card.Text style={{marginTop: '4px', marginLeft: '2rem'}}>Новосибирск</Card.Text>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Количество объявлений:</Card.Subtitle>
                                    <a href={'post-page'}>
                                        <Card.Text style={{marginTop: '4px', marginLeft: '2rem'}}>
                                            {this.state.postsNumber}
                                        </Card.Text>
                                    </a>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Количество подписчиков:</Card.Subtitle>
                                    <a href={""}>
                                        <Card.Text style={{marginTop: '4px', marginLeft: '2rem'}}>
                                            {this.state.subscribersNumber}
                                        </Card.Text>
                                    </a>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Количество подписок:</Card.Subtitle>
                                    <a href={""}>
                                        <Card.Text style={{marginTop: '4px', marginLeft: '2rem'}}>
                                            {this.state.subscriptionsNumber}
                                        </Card.Text>
                                    </a>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Обо мне:</Card.Subtitle>
                                    <Card.Text style={{marginTop: '4px', marginLeft: '2rem'}}>{this.state.aboutMe}</Card.Text>

                                    <Card.Subtitle style={{marginTop: '1rem'}}>Рейтинг:</Card.Subtitle>
                                    <Card.Text style={{marginTop: '4px', marginLeft: '2rem'}}>{this.state.rating}</Card.Text>
                                </div>
                            </Card.Body>
                        </Col>
                    </Row>
                </Card>
            </div>

        )
    }
}