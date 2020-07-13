import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISlide, defaultValue } from 'app/shared/model/slide.model';

export const ACTION_TYPES = {
  FETCH_SLIDE_LIST: 'slide/FETCH_SLIDE_LIST',
  FETCH_SLIDE: 'slide/FETCH_SLIDE',
  CREATE_SLIDE: 'slide/CREATE_SLIDE',
  UPDATE_SLIDE: 'slide/UPDATE_SLIDE',
  DELETE_SLIDE: 'slide/DELETE_SLIDE',
  RESET: 'slide/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISlide>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SlideState = Readonly<typeof initialState>;

// Reducer

export default (state: SlideState = initialState, action): SlideState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SLIDE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SLIDE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SLIDE):
    case REQUEST(ACTION_TYPES.UPDATE_SLIDE):
    case REQUEST(ACTION_TYPES.DELETE_SLIDE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SLIDE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SLIDE):
    case FAILURE(ACTION_TYPES.CREATE_SLIDE):
    case FAILURE(ACTION_TYPES.UPDATE_SLIDE):
    case FAILURE(ACTION_TYPES.DELETE_SLIDE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SLIDE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SLIDE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SLIDE):
    case SUCCESS(ACTION_TYPES.UPDATE_SLIDE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SLIDE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/slides';

// Actions

export const getEntities: ICrudGetAllAction<ISlide> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SLIDE_LIST,
  payload: axios.get<ISlide>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISlide> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SLIDE,
    payload: axios.get<ISlide>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISlide> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SLIDE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISlide> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SLIDE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISlide> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SLIDE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
