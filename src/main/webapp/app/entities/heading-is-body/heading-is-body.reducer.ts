import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHeadingIsBody, defaultValue } from 'app/shared/model/heading-is-body.model';

export const ACTION_TYPES = {
  FETCH_HEADINGISBODY_LIST: 'headingIsBody/FETCH_HEADINGISBODY_LIST',
  FETCH_HEADINGISBODY: 'headingIsBody/FETCH_HEADINGISBODY',
  CREATE_HEADINGISBODY: 'headingIsBody/CREATE_HEADINGISBODY',
  UPDATE_HEADINGISBODY: 'headingIsBody/UPDATE_HEADINGISBODY',
  DELETE_HEADINGISBODY: 'headingIsBody/DELETE_HEADINGISBODY',
  RESET: 'headingIsBody/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHeadingIsBody>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type HeadingIsBodyState = Readonly<typeof initialState>;

// Reducer

export default (state: HeadingIsBodyState = initialState, action): HeadingIsBodyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HEADINGISBODY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HEADINGISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_HEADINGISBODY):
    case REQUEST(ACTION_TYPES.UPDATE_HEADINGISBODY):
    case REQUEST(ACTION_TYPES.DELETE_HEADINGISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_HEADINGISBODY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HEADINGISBODY):
    case FAILURE(ACTION_TYPES.CREATE_HEADINGISBODY):
    case FAILURE(ACTION_TYPES.UPDATE_HEADINGISBODY):
    case FAILURE(ACTION_TYPES.DELETE_HEADINGISBODY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_HEADINGISBODY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_HEADINGISBODY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_HEADINGISBODY):
    case SUCCESS(ACTION_TYPES.UPDATE_HEADINGISBODY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_HEADINGISBODY):
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

const apiUrl = 'api/heading-is-bodies';

// Actions

export const getEntities: ICrudGetAllAction<IHeadingIsBody> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HEADINGISBODY_LIST,
  payload: axios.get<IHeadingIsBody>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IHeadingIsBody> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HEADINGISBODY,
    payload: axios.get<IHeadingIsBody>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IHeadingIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HEADINGISBODY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHeadingIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HEADINGISBODY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHeadingIsBody> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HEADINGISBODY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
