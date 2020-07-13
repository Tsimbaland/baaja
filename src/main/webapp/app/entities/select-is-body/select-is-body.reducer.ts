import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISelectIsBody, defaultValue } from 'app/shared/model/select-is-body.model';

export const ACTION_TYPES = {
  FETCH_SELECTISBODY_LIST: 'selectIsBody/FETCH_SELECTISBODY_LIST',
  FETCH_SELECTISBODY: 'selectIsBody/FETCH_SELECTISBODY',
  CREATE_SELECTISBODY: 'selectIsBody/CREATE_SELECTISBODY',
  UPDATE_SELECTISBODY: 'selectIsBody/UPDATE_SELECTISBODY',
  DELETE_SELECTISBODY: 'selectIsBody/DELETE_SELECTISBODY',
  RESET: 'selectIsBody/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISelectIsBody>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SelectIsBodyState = Readonly<typeof initialState>;

// Reducer

export default (state: SelectIsBodyState = initialState, action): SelectIsBodyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SELECTISBODY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SELECTISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SELECTISBODY):
    case REQUEST(ACTION_TYPES.UPDATE_SELECTISBODY):
    case REQUEST(ACTION_TYPES.DELETE_SELECTISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SELECTISBODY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SELECTISBODY):
    case FAILURE(ACTION_TYPES.CREATE_SELECTISBODY):
    case FAILURE(ACTION_TYPES.UPDATE_SELECTISBODY):
    case FAILURE(ACTION_TYPES.DELETE_SELECTISBODY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SELECTISBODY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SELECTISBODY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SELECTISBODY):
    case SUCCESS(ACTION_TYPES.UPDATE_SELECTISBODY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SELECTISBODY):
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

const apiUrl = 'api/select-is-bodies';

// Actions

export const getEntities: ICrudGetAllAction<ISelectIsBody> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SELECTISBODY_LIST,
  payload: axios.get<ISelectIsBody>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISelectIsBody> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SELECTISBODY,
    payload: axios.get<ISelectIsBody>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISelectIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SELECTISBODY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISelectIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SELECTISBODY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISelectIsBody> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SELECTISBODY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
