import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './presentation.reducer';
import { IPresentation } from 'app/shared/model/presentation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPresentationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PresentationDetail = (props: IPresentationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { presentationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Presentation [<b>{presentationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{presentationEntity.title}</dd>
          <dt>
            <span id="dateCreated">Date Created</span>
          </dt>
          <dd>
            <TextFormat value={presentationEntity.dateCreated} type="date" format={APP_LOCAL_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="dateUpdated">Date Updated</span>
          </dt>
          <dd>
            <TextFormat value={presentationEntity.dateUpdated} type="date" format={APP_LOCAL_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{presentationEntity.description}</dd>
          <dt>Author</dt>
          <dd>{presentationEntity.authorId ? presentationEntity.authorId : ''}</dd>
        </dl>
        <Button tag={Link} to="/presentation" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/presentation/${presentationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ presentation }: IRootState) => ({
  presentationEntity: presentation.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PresentationDetail);
