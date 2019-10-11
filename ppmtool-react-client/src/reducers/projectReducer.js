import { GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "../actions/types";

const initialState = {
  projects: [],
  project: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case DELETE_PROJECT:
      return {
        ...state,
        projects: state.projects.filter(
          project => project.projectIdentifier !== action.payload
        )
      };
    case GET_PROJECTS:
      return {
        ...state,
        projects: action.payload
      };
    case GET_PROJECT:
      return {
        ...state,
        project: action.payload
      };
    default:
      return state;
  }
}
