import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './presentation.reducer';
import { IPresentation } from 'app/shared/model/presentation.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPresentationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PresentationUpdate = (props: IPresentationUpdateProps) => {
  const [authorId, setAuthorId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { presentationEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/presentation' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...presentationEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="baajaApp.presentation.home.createOrEditLabel">Create or edit a Presentation</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : presentationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="presentation-id">ID</Label>
                  <AvInput id="presentation-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="presentation-title">
                  Title
                </Label>
                <AvField id="presentation-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="dateCreatedLabel" for="presentation-dateCreated">
                  Date Created
                </Label>
                <AvField id="presentation-dateCreated" type="date" className="form-control" name="dateCreated" />
              </AvGroup>
              <AvGroup>
                <Label id="dateUpdatedLabel" for="presentation-dateUpdated">
                  Date Updated
                </Label>
                <AvField id="presentation-dateUpdated" type="date" className="form-control" name="dateUpdated" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="presentation-description">
                  Description
                </Label>
                <AvField id="presentation-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="presentation-author">Author</Label>
                <AvInput id="presentation-author" type="select" className="form-control" name="authorId" required>
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/presentation" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  presentationEntity: storeState.presentation.entity,
  loading: storeState.presentation.loading,
  updating: storeState.presentation.updating,
  updateSuccess: storeState.presentation.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PresentationUpdate);
