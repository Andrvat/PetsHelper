import {Button, FormText, InputGroup, Nav, NavDropdown} from "react-bootstrap";
import React, {Component} from "react";
import {withRouter} from "../../utilites/withRouter";

class HomeNavbar extends Component {
    state = {
        tags: "",
    };

    constructor(props) {
        super(props);

        this.handleChange = this.handleChange.bind(this);
        this.onClick = this.onClick.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const name = target.name;
        this.setState({[name]: target.value});
    }

    onClick(event) {
        // console.log("this.state.tags", this.state.tags);

        const [, setSearchParams] = this.props.searchParams;
        setSearchParams({tags: this.state.tags});

        // const navigate = this.props.navigate;
        // navigate(
        //   { pathname: "/post/all/all", search: `?tags=${this.state.tags}` },
        //   { replace: false }
        // );
    }

    render() {
        console.log("isAdmin = " + localStorage.getItem("isAdmin"));
        return (
            <nav className="navbar navbar-expand-lg navbar-light fixed-top">
                <div className="container w-100">
                    <img
                        className="m-lg-auto"
                        width="40px"
                        src="/images/cat_1.svg"
                        alt="icon"
                    />
                    <Nav.Link className="navbar-brand px-4" href="/post/all/all">
                        PetsHelper
                    </Nav.Link>
                    <div className="basic-navbar-nav"/>
                    <div
                        className="collapse navbar-collapse d-flex"
                        id="navbarTogglerDemo02"
                    >
                        <nav className="navbar-nav me-auto">
                            <NavDropdown
                                className="navbar-nav d-flex mx-2"
                                title="Объявления"
                                id="basic-nav-dropdown"
                            >
                                <NavDropdown.Item href="/post/bulletin-board/give">
                                    Отдам в добрые руки
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/take">
                                    Возьму в добрые руки
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/lost">
                                    Потеряшки
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/help">
                                    Передержка / Перевозка
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/fundraising">
                                    Сбор средств
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/goods">
                                    Товары
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/bulletin-board/services">
                                    Услуги
                                </NavDropdown.Item>
                            </NavDropdown>

                            <NavDropdown
                                className="navbar-nav d-flex mx-2"
                                title="Мероприятия"
                                id="basic-nav-dropdown"
                            >
                                <NavDropdown.Item href="/post/events/competitions">
                                    Конкурсы
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/events/exhibitions">
                                    Выставки
                                </NavDropdown.Item>
                            </NavDropdown>

                            <NavDropdown
                                className="navbar-nav d-flex mx-2"
                                title="Вопросы-ответы"
                                id="basic-nav-dropdown"
                            >
                                <NavDropdown.Item href="/post/forum/veterinary">
                                    Ветеринария
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/forum/management">
                                    Содержание
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/forum/behavior-and-upbringing">
                                    Поведение и воспитание
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/forum/reproduction">
                                    Продолжение рода
                                </NavDropdown.Item>
                            </NavDropdown>

                            <NavDropdown
                                className="navbar-nav d-flex mx-2"
                                title="Организации"
                                id="basic-nav-dropdown"
                            >
                                <NavDropdown.Item href="/post/organizations/charity-fund">
                                    Благотворительные фонды
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/organizations/nurseries">
                                    Питомники
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/post/organizations/shelters">
                                    Приюты
                                </NavDropdown.Item>
                            </NavDropdown>

                            <input
                                className="form-control"
                                name="tags"
                                placeholder={this.state.tags}
                                style={{width: "150"}}
                                onChange={this.handleChange}
                            />
                            <img
                                src="/images/search.png"
                                alt="icon"
                                style={{width: "27px", height: "27px", margin: "7px"}}
                                onClick={this.onClick}
                            />
                        </nav>
                    </div>

                    <div
                        className="collapse navbar-collapse d-flex  flex-row-reverse"
                        id="navbarTogglerDemo02"
                    >
                        <nav className="navbar-nav">
                            {localStorage.getItem("isAdmin") ?
                                <div>
                                    <Nav.Link className="navbar-nav d-flex mx-2"
                                              href="/post/moderate/all">
                                        Очередь запросов
                                    </Nav.Link>
                                </div>
                                : <div>
                                    <NavDropdown
                                        className="navbar-nav d-flex mx-2"
                                        title="Мой профиль"
                                        id="basic-nav-dropdown"
                                    >
                                        <NavDropdown.Item href={"/user/" + localStorage.getItem("username")}>
                                            Просмотр профиля
                                        </NavDropdown.Item>
                                        <NavDropdown.Item href={"/user/post/create"}>
                                            Новое объявление
                                        </NavDropdown.Item>
                                    </NavDropdown>
                                </div>
                            }
                            <a href={"/auth/sign-in"}>
                                <img
                                    className="mx-2"
                                    width="40px"
                                    src="/images/icons8-log-out-64.png"
                                    alt="icon"
                                />
                            </a>
                        </nav>
                    </div>
                </div>
            </nav>
        );
    }
}

export default withRouter(HomeNavbar);
