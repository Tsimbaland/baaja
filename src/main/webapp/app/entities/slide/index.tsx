import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Slide from './slide';
import SlideDetail from './slide-detail';
import SlideUpdate from './slide-update';
import SlideDeleteDialog from './slide-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SlideDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SlideUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SlideUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SlideDetail} />
      <ErrorBoundaryRoute path={match.url} component={Slide} />
    </Switch>
  </>
);

export default Routes;
