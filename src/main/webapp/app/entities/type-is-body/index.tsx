import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TypeIsBody from './type-is-body';
import TypeIsBodyDetail from './type-is-body-detail';
import TypeIsBodyUpdate from './type-is-body-update';
import TypeIsBodyDeleteDialog from './type-is-body-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TypeIsBodyDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TypeIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TypeIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TypeIsBodyDetail} />
      <ErrorBoundaryRoute path={match.url} component={TypeIsBody} />
    </Switch>
  </>
);

export default Routes;
