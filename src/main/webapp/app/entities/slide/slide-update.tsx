import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBody } from 'app/shared/model/body.model';
import { getEntities as getBodies } from 'app/entities/body/body.reducer';
import { IPresentation } from 'app/shared/model/presentation.model';
import { getEntities as getPresentations } from 'app/entities/presentation/presentation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './slide.reducer';
import { ISlide } from 'app/shared/model/slide.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISlideUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SlideUpdate = (props: ISlideUpdateProps) => {
  const [bodyId, setBodyId] = useState('0');
  const [presentationId, setPresentationId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { slideEntity, bodies, presentations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/slide');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBodies();
    props.getPresentations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...slideEntity,
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
          <h2 id="baajaApp.slide.home.createOrEditLabel">Create or edit a Slide</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : slideEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="slide-id">ID</Label>
                  <AvInput id="slide-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="orderLabel" for="slide-order">
                  Order
                </Label>
                <AvField id="slide-order" type="string" className="form-control" name="order" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="slide-name">
                  Name
                </Label>
                <AvField id="slide-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="slide-type">
                  Type
                </Label>
                <AvInput
                  id="slide-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && slideEntity.type) || 'SELECT_ANSWER'}
                >
                  <option value="SELECT_ANSWER">SELECT_ANSWER</option>
                  <option value="TYPE_ANSWER">TYPE_ANSWER</option>
                  <option value="HEADING">HEADING</option>
                  <option value="PARAGRAPH">PARAGRAPH</option>
                  <option value="BULLETS">BULLETS</option>
                  <option value="IMAGE">IMAGE</option>
                  <option value="BULLETS_WITH_IMAGE">BULLETS_WITH_IMAGE</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="slide-body">Body</Label>
                <AvInput id="slide-body" type="select" className="form-control" name="bodyId">
                  <option value="" key="0" />
                  {bodies
                    ? bodies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="slide-presentation">Presentation</Label>
                <AvInput id="slide-presentation" type="select" className="form-control" name="presentationId">
                  <option value="" key="0" />
                  {presentations
                    ? presentations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/slide" replace color="info">
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
  bodies: storeState.body.entities,
  presentations: storeState.presentation.entities,
  slideEntity: storeState.slide.entity,
  loading: storeState.slide.loading,
  updating: storeState.slide.updating,
  updateSuccess: storeState.slide.updateSuccess
});

const mapDispatchToProps = {
  getBodies,
  getPresentations,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SlideUpdate);
