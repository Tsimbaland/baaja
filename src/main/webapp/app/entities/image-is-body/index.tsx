import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ImageIsBody from './image-is-body';
import ImageIsBodyDetail from './image-is-body-detail';
import ImageIsBodyUpdate from './image-is-body-update';
import ImageIsBodyDeleteDialog from './image-is-body-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ImageIsBodyDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ImageIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ImageIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ImageIsBodyDetail} />
      <ErrorBoundaryRoute path={match.url} component={ImageIsBody} />
    </Switch>
  </>
);

export default Routes;
