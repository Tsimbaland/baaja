import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBody, defaultValue } from 'app/shared/model/body.model';

export const ACTION_TYPES = {
  FETCH_BODY_LIST: 'body/FETCH_BODY_LIST',
  FETCH_BODY: 'body/FETCH_BODY',
  CREATE_BODY: 'body/CREATE_BODY',
  UPDATE_BODY: 'body/UPDATE_BODY',
  DELETE_BODY: 'body/DELETE_BODY',
  RESET: 'body/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBody>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BodyState = Readonly<typeof initialState>;

// Reducer

export default (state: BodyState = initialState, action): BodyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BODY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BODY):
    case REQUEST(ACTION_TYPES.UPDATE_BODY):
    case REQUEST(ACTION_TYPES.DELETE_BODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BODY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BODY):
    case FAILURE(ACTION_TYPES.CREATE_BODY):
    case FAILURE(ACTION_TYPES.UPDATE_BODY):
    case FAILURE(ACTION_TYPES.DELETE_BODY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BODY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BODY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BODY):
    case SUCCESS(ACTION_TYPES.UPDATE_BODY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BODY):
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

const apiUrl = 'api/bodies';

// Actions

export const getEntities: ICrudGetAllAction<IBody> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BODY_LIST,
  payload: axios.get<IBody>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBody> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BODY,
    payload: axios.get<IBody>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BODY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BODY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBody> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BODY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
