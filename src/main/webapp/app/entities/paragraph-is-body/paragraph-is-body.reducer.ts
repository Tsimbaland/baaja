import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParagraphIsBody, defaultValue } from 'app/shared/model/paragraph-is-body.model';

export const ACTION_TYPES = {
  FETCH_PARAGRAPHISBODY_LIST: 'paragraphIsBody/FETCH_PARAGRAPHISBODY_LIST',
  FETCH_PARAGRAPHISBODY: 'paragraphIsBody/FETCH_PARAGRAPHISBODY',
  CREATE_PARAGRAPHISBODY: 'paragraphIsBody/CREATE_PARAGRAPHISBODY',
  UPDATE_PARAGRAPHISBODY: 'paragraphIsBody/UPDATE_PARAGRAPHISBODY',
  DELETE_PARAGRAPHISBODY: 'paragraphIsBody/DELETE_PARAGRAPHISBODY',
  RESET: 'paragraphIsBody/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParagraphIsBody>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ParagraphIsBodyState = Readonly<typeof initialState>;

// Reducer

export default (state: ParagraphIsBodyState = initialState, action): ParagraphIsBodyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PARAGRAPHISBODY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARAGRAPHISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARAGRAPHISBODY):
    case REQUEST(ACTION_TYPES.UPDATE_PARAGRAPHISBODY):
    case REQUEST(ACTION_TYPES.DELETE_PARAGRAPHISBODY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PARAGRAPHISBODY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARAGRAPHISBODY):
    case FAILURE(ACTION_TYPES.CREATE_PARAGRAPHISBODY):
    case FAILURE(ACTION_TYPES.UPDATE_PARAGRAPHISBODY):
    case FAILURE(ACTION_TYPES.DELETE_PARAGRAPHISBODY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARAGRAPHISBODY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARAGRAPHISBODY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARAGRAPHISBODY):
    case SUCCESS(ACTION_TYPES.UPDATE_PARAGRAPHISBODY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARAGRAPHISBODY):
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

const apiUrl = 'api/paragraph-is-bodies';

// Actions

export const getEntities: ICrudGetAllAction<IParagraphIsBody> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PARAGRAPHISBODY_LIST,
  payload: axios.get<IParagraphIsBody>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IParagraphIsBody> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARAGRAPHISBODY,
    payload: axios.get<IParagraphIsBody>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParagraphIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARAGRAPHISBODY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IParagraphIsBody> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARAGRAPHISBODY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParagraphIsBody> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARAGRAPHISBODY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
