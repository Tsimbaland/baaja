import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPresentation, defaultValue } from 'app/shared/model/presentation.model';

export const ACTION_TYPES = {
  FETCH_PRESENTATION_LIST: 'presentation/FETCH_PRESENTATION_LIST',
  FETCH_PRESENTATION: 'presentation/FETCH_PRESENTATION',
  CREATE_PRESENTATION: 'presentation/CREATE_PRESENTATION',
  UPDATE_PRESENTATION: 'presentation/UPDATE_PRESENTATION',
  DELETE_PRESENTATION: 'presentation/DELETE_PRESENTATION',
  RESET: 'presentation/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPresentation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PresentationState = Readonly<typeof initialState>;

// Reducer

export default (state: PresentationState = initialState, action): PresentationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRESENTATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRESENTATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PRESENTATION):
    case REQUEST(ACTION_TYPES.UPDATE_PRESENTATION):
    case REQUEST(ACTION_TYPES.DELETE_PRESENTATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PRESENTATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRESENTATION):
    case FAILURE(ACTION_TYPES.CREATE_PRESENTATION):
    case FAILURE(ACTION_TYPES.UPDATE_PRESENTATION):
    case FAILURE(ACTION_TYPES.DELETE_PRESENTATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRESENTATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRESENTATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRESENTATION):
    case SUCCESS(ACTION_TYPES.UPDATE_PRESENTATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRESENTATION):
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

const apiUrl = 'api/presentations';

// Actions

export const getEntities: ICrudGetAllAction<IPresentation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRESENTATION_LIST,
    payload: axios.get<IPresentation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPresentation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRESENTATION,
    payload: axios.get<IPresentation>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPresentation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRESENTATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPresentation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRESENTATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPresentation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRESENTATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
