import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBulletsIsBody, defaultValue } from 'app/shared/model/bullets-is-body.model';

export const ACTION_TYPES = {
  FETCH_BULLETSISBODY_LIST: 'bulletsIsBody/FETCH_BULLETSISBODY_LIST',
  FETCH_BULLETSISBODY: 'bulletsIsBody/FETCH_BULLETSISBODY',
  CREATE_BULLETSISBODY: 'bulletsIsBody/CREATE_BULLETSISBODY',
  UPDATE_BULLETSISBODY: 'bulletsIsBody/UPDATE_BULLETSISBODY',
  DELETE_BULLETSISBODY: 'bulletsIsBody/DELETE_BULLETSISBODY',
  RESET: 'bulletsIsBody/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBulletsIsBody>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BulletsIsBodyState = Readonly<typeof initialState>;

// Reducer

export default (state: BulletsIsBodyState = initialState, action): BulletsIsBodyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BULLETSISBODY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BULLETSISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BULLETSISBODY):
    case REQUEST(ACTION_TYPES.UPDATE_BULLETSISBODY):
    case REQUEST(ACTION_TYPES.DELETE_BULLETSISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BULLETSISBODY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BULLETSISBODY):
    case FAILURE(ACTION_TYPES.CREATE_BULLETSISBODY):
    case FAILURE(ACTION_TYPES.UPDATE_BULLETSISBODY):
    case FAILURE(ACTION_TYPES.DELETE_BULLETSISBODY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BULLETSISBODY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BULLETSISBODY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BULLETSISBODY):
    case SUCCESS(ACTION_TYPES.UPDATE_BULLETSISBODY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BULLETSISBODY):
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

const apiUrl = 'api/bullets-is-bodies';

// Actions

export const getEntities: ICrudGetAllAction<IBulletsIsBody> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BULLETSISBODY_LIST,
  payload: axios.get<IBulletsIsBody>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBulletsIsBody> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BULLETSISBODY,
    payload: axios.get<IBulletsIsBody>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBulletsIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BULLETSISBODY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBulletsIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BULLETSISBODY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBulletsIsBody> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BULLETSISBODY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
