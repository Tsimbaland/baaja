import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SelectIsBody from './select-is-body';
import SelectIsBodyDetail from './select-is-body-detail';
import SelectIsBodyUpdate from './select-is-body-update';
import SelectIsBodyDeleteDialog from './select-is-body-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SelectIsBodyDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SelectIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SelectIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SelectIsBodyDetail} />
      <ErrorBoundaryRoute path={match.url} component={SelectIsBody} />
    </Switch>
  </>
);

export default Routes;
