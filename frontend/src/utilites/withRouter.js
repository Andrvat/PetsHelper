import { useParams, useNavigate, useSearchParams } from "react-router-dom";

export function withRouter(Child) {
  return (props) => {
    const params = useParams();
    const navigate = useNavigate();
    const searchParams = useSearchParams();
    const searchParamsFunc = useSearchParams;
    return (
      <Child
        {...props}
        params={params}
        navigate={navigate}
        searchParams={searchParams}
        searchParamsFunc={searchParamsFunc}
      />
    );
  };
}
