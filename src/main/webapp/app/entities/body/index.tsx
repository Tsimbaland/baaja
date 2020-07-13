import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Body from './body';
import BodyDetail from './body-detail';
import BodyUpdate from './body-update';
import BodyDeleteDialog from './body-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BodyDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BodyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Body} />
    </Switch>
  </>
);

export default Routes;
