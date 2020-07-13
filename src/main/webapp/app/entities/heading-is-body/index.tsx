import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HeadingIsBody from './heading-is-body';
import HeadingIsBodyDetail from './heading-is-body-detail';
import HeadingIsBodyUpdate from './heading-is-body-update';
import HeadingIsBodyDeleteDialog from './heading-is-body-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HeadingIsBodyDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HeadingIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HeadingIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HeadingIsBodyDetail} />
      <ErrorBoundaryRoute path={match.url} component={HeadingIsBody} />
    </Switch>
  </>
);

export default Routes;
