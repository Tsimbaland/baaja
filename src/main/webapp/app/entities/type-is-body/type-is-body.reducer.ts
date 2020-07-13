import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITypeIsBody, defaultValue } from 'app/shared/model/type-is-body.model';

export const ACTION_TYPES = {
  FETCH_TYPEISBODY_LIST: 'typeIsBody/FETCH_TYPEISBODY_LIST',
  FETCH_TYPEISBODY: 'typeIsBody/FETCH_TYPEISBODY',
  CREATE_TYPEISBODY: 'typeIsBody/CREATE_TYPEISBODY',
  UPDATE_TYPEISBODY: 'typeIsBody/UPDATE_TYPEISBODY',
  DELETE_TYPEISBODY: 'typeIsBody/DELETE_TYPEISBODY',
  RESET: 'typeIsBody/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITypeIsBody>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TypeIsBodyState = Readonly<typeof initialState>;

// Reducer

export default (state: TypeIsBodyState = initialState, action): TypeIsBodyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TYPEISBODY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TYPEISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TYPEISBODY):
    case REQUEST(ACTION_TYPES.UPDATE_TYPEISBODY):
    case REQUEST(ACTION_TYPES.DELETE_TYPEISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TYPEISBODY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TYPEISBODY):
    case FAILURE(ACTION_TYPES.CREATE_TYPEISBODY):
    case FAILURE(ACTION_TYPES.UPDATE_TYPEISBODY):
    case FAILURE(ACTION_TYPES.DELETE_TYPEISBODY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPEISBODY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPEISBODY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TYPEISBODY):
    case SUCCESS(ACTION_TYPES.UPDATE_TYPEISBODY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TYPEISBODY):
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

const apiUrl = 'api/type-is-bodies';

// Actions

export const getEntities: ICrudGetAllAction<ITypeIsBody> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TYPEISBODY_LIST,
  payload: axios.get<ITypeIsBody>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITypeIsBody> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TYPEISBODY,
    payload: axios.get<ITypeIsBody>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITypeIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TYPEISBODY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITypeIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TYPEISBODY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITypeIsBody> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TYPEISBODY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
