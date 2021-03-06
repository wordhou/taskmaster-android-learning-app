package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TaskData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TaskData")
@Index(name = "byTeam", fields = {"teamID"})
public final class TaskData implements Model {
  public static final QueryField ID = field("TaskData", "id");
  public static final QueryField NAME = field("TaskData", "name");
  public static final QueryField DESCRIPTION = field("TaskData", "description");
  public static final QueryField STATUS = field("TaskData", "status");
  public static final QueryField HAS_PICTURE = field("TaskData", "hasPicture");
  public static final QueryField TEAM = field("TaskData", "teamID");
  public static final QueryField CREATED_AT = field("TaskData", "createdAt");
  public static final QueryField UPDATED_AT = field("TaskData", "updatedAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="Status") Status status;
  private @ModelField(targetType="Boolean") Boolean hasPicture = false; // ADDED MANUAL DEFAULT
  private final @ModelField(targetType="TeamData") @BelongsTo(targetName = "teamID", type = TeamData.class) TeamData team;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Status getStatus() {
      return status;
  }
  
  public Boolean getHasPicture() {
      return hasPicture;
  }
  
  public TeamData getTeam() {
      return team;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TaskData(String id, String name, String description, Status status, Boolean hasPicture, TeamData team, Temporal.DateTime createdAt, Temporal.DateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.status = status;
    this.hasPicture = hasPicture;
    this.team = team;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TaskData taskData = (TaskData) obj;
      return ObjectsCompat.equals(getId(), taskData.getId()) &&
              ObjectsCompat.equals(getName(), taskData.getName()) &&
              ObjectsCompat.equals(getDescription(), taskData.getDescription()) &&
              ObjectsCompat.equals(getStatus(), taskData.getStatus()) &&
              ObjectsCompat.equals(getHasPicture(), taskData.getHasPicture()) &&
              ObjectsCompat.equals(getTeam(), taskData.getTeam()) &&
              ObjectsCompat.equals(getCreatedAt(), taskData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), taskData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getStatus())
      .append(getHasPicture())
      .append(getTeam())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TaskData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("hasPicture=" + String.valueOf(getHasPicture()) + ", ")
      .append("team=" + String.valueOf(getTeam()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static TaskData justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new TaskData(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      status,
      hasPicture,
      team,
      createdAt,
      updatedAt);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    TaskData build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep description(String description);
    BuildStep status(Status status);
    BuildStep hasPicture(Boolean hasPicture);
    BuildStep team(TeamData team);
    BuildStep createdAt(Temporal.DateTime createdAt);
    BuildStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private Status status;
    private Boolean hasPicture;
    private TeamData team;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    @Override
     public TaskData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TaskData(
          id,
          name,
          description,
          status,
          hasPicture,
          team,
          createdAt,
          updatedAt);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep status(Status status) {
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep hasPicture(Boolean hasPicture) {
        this.hasPicture = hasPicture;
        return this;
    }
    
    @Override
     public BuildStep team(TeamData team) {
        this.team = team;
        return this;
    }
    
    @Override
     public BuildStep createdAt(Temporal.DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep updatedAt(Temporal.DateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String description, Status status, Boolean hasPicture, TeamData team, Temporal.DateTime createdAt, Temporal.DateTime updatedAt) {
      super.id(id);
      super.name(name)
        .description(description)
        .status(status)
        .hasPicture(hasPicture)
        .team(team)
        .createdAt(createdAt)
        .updatedAt(updatedAt);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder status(Status status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder hasPicture(Boolean hasPicture) {
      return (CopyOfBuilder) super.hasPicture(hasPicture);
    }
    
    @Override
     public CopyOfBuilder team(TeamData team) {
      return (CopyOfBuilder) super.team(team);
    }
    
    @Override
     public CopyOfBuilder createdAt(Temporal.DateTime createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder updatedAt(Temporal.DateTime updatedAt) {
      return (CopyOfBuilder) super.updatedAt(updatedAt);
    }
  }
  
}
