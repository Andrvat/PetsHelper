import {Nav, NavDropdown} from "react-bootstrap";
import React, {Component} from 'react';
import loginIcon from "../../../images/cat_1.svg";
import logOutIcon from "../../../images/icons8-log-out-64.png"
import {BrowserRouter, Link, Route} from "react-router-dom";
import HomePage from "../../home/home_page";
import SignIn from "../../auth/sign_in/sign_in";
import UserProfile from "./user_profile";


export default class UserNavbar extends Component {
    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-light fixed-top">
                <div className="container w-100">
                    <img className="m-lg-auto" width="40px" src={loginIcon} alt="icon"/>
                    {/*<div className="navbar-brand px-4">*/}
                    <Nav.Link className="navbar-brand px-4" href="/post/all/all">PetsHelper</Nav.Link>
                    {/*</div>*/}
                    <div className="basic-navbar-nav"/>
                    <div className="collapse navbar-collapse d-flex" id="navbarTogglerDemo02">
                        <nav className="navbar-nav me-auto">

                            <NavDropdown className="navbar-nav d-flex mx-2" title="Объявления"
                                         id="basic-nav-dropdown">
                                <NavDropdown.Item href="/post/bulletin-board/give">Отдам в добрые
                                    руки</NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/take">Возьму в добрые
                                    руки</NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/lost">Потеряшки</NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/help">Передержка /
                                    Перевозка</NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/fundraising">Сбор
                                    средств</NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/goods">Товары</NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/services">Услуги</NavDropdown.Item>
                            </NavDropdown>

                            <NavDropdown className="navbar-nav d-flex mx-2" title="Мероприятия"
                                         id="basic-nav-dropdown">
                                <NavDropdown.Item href="/post/events/competitions">Конкурсы</NavDropdown.Item>
                                <NavDropdown.Item href="/post/events/exhibitions">Выставки</NavDropdown.Item>
                            </NavDropdown>

                            <NavDropdown className="navbar-nav d-flex mx-2" title="Вопросы-ответы"
                                         id="basic-nav-dropdown">
                                <NavDropdown.Item href="/post/forum/veterinary">Ветеринария</NavDropdown.Item>
                                <NavDropdown.Item href="/post/forum/management">Содержание</NavDropdown.Item>
                                <NavDropdown.Item href="/post/forum/behavior-and-upbringing">Поведение и
                                    воспитание</NavDropdown.Item>
                                <NavDropdown.Item href="/post/forum/reproduction">Продолжение рода</NavDropdown.Item>
                            </NavDropdown>

                            <NavDropdown className="navbar-nav d-flex mx-2" title="Организации"
                                         id="basic-nav-dropdown">
                                <NavDropdown.Item href="/post/organizations/charity-fund">Благотворительные
                                    фонды</NavDropdown.Item>
                                <NavDropdown.Item href="/post/organizations/nurseries">Питомники</NavDropdown.Item>
                                <NavDropdown.Item href="/post/organizations/shelters">Приюты</NavDropdown.Item>
                            </NavDropdown>

                        </nav>
                    </div>
                    <div className="collapse navbar-collapse d-flex  flex-row-reverse"
                         id="navbarTogglerDemo02">
                        <nav className="navbar-nav">
                            <NavDropdown className="navbar-nav d-flex mx-2" title="Мой профиль"
                                         id="basic-nav-dropdown">
                                <NavDropdown.Item href={"/user/" + localStorage.getItem("username")}>Просмотр профиля</NavDropdown.Item>
                                <NavDropdown.Item href={"/user/post/create"}>Новое объявление</NavDropdown.Item>
                            </NavDropdown>
                            <a href={"/auth/sign-in"}>
                                <img className="mx-2" width="40px" src={logOutIcon} alt="icon"/>
                            </a>
                        </nav>
                    </div>
                </div>
            </nav>
        )
    }
}
