import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IImageIsBody, defaultValue } from 'app/shared/model/image-is-body.model';

export const ACTION_TYPES = {
  FETCH_IMAGEISBODY_LIST: 'imageIsBody/FETCH_IMAGEISBODY_LIST',
  FETCH_IMAGEISBODY: 'imageIsBody/FETCH_IMAGEISBODY',
  CREATE_IMAGEISBODY: 'imageIsBody/CREATE_IMAGEISBODY',
  UPDATE_IMAGEISBODY: 'imageIsBody/UPDATE_IMAGEISBODY',
  DELETE_IMAGEISBODY: 'imageIsBody/DELETE_IMAGEISBODY',
  RESET: 'imageIsBody/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IImageIsBody>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ImageIsBodyState = Readonly<typeof initialState>;

// Reducer

export default (state: ImageIsBodyState = initialState, action): ImageIsBodyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_IMAGEISBODY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_IMAGEISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_IMAGEISBODY):
    case REQUEST(ACTION_TYPES.UPDATE_IMAGEISBODY):
    case REQUEST(ACTION_TYPES.DELETE_IMAGEISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_IMAGEISBODY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_IMAGEISBODY):
    case FAILURE(ACTION_TYPES.CREATE_IMAGEISBODY):
    case FAILURE(ACTION_TYPES.UPDATE_IMAGEISBODY):
    case FAILURE(ACTION_TYPES.DELETE_IMAGEISBODY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_IMAGEISBODY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_IMAGEISBODY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_IMAGEISBODY):
    case SUCCESS(ACTION_TYPES.UPDATE_IMAGEISBODY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_IMAGEISBODY):
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

const apiUrl = 'api/image-is-bodies';

// Actions

export const getEntities: ICrudGetAllAction<IImageIsBody> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_IMAGEISBODY_LIST,
  payload: axios.get<IImageIsBody>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IImageIsBody> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_IMAGEISBODY,
    payload: axios.get<IImageIsBody>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IImageIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_IMAGEISBODY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IImageIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_IMAGEISBODY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IImageIsBody> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_IMAGEISBODY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
