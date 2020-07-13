import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParagraphIsBody from './paragraph-is-body';
import ParagraphIsBodyDetail from './paragraph-is-body-detail';
import ParagraphIsBodyUpdate from './paragraph-is-body-update';
import ParagraphIsBodyDeleteDialog from './paragraph-is-body-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ParagraphIsBodyDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParagraphIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParagraphIsBodyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParagraphIsBodyDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParagraphIsBody} />
    </Switch>
  </>
);

export default Routes;
