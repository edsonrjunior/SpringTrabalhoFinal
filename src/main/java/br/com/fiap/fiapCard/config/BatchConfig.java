package br.com.fiap.fiapCard.config;


import br.com.fiap.fiapCard.model.Aluno;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   Step step) {
        return jobBuilderFactory.get("Carregar base de alunos")
                .start(step)
                .build();
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory,
                     ItemReader<Aluno> itemReader,
                     ItemWriter<Aluno> itemWriter) {

        return stepBuilderFactory.get("Aluno step")
                .<Aluno, Aluno>chunk(2)
                .reader(itemReader)
                .writer(itemWriter)
                .faultTolerant()
                .skipLimit(1000000000)
                .skip(Exception.class)
                .build();
    }

    @Bean
    public FlatFileItemReader<Aluno> itemReader(@Value("${file.input}") Resource resource) {
        return new FlatFileItemReaderBuilder<Aluno>()
                .name("file item reader")
                .targetType(Aluno.class)
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .resource(resource)
                .build();
    }

    @Bean
    public LineMapper<Aluno> lineMapper() {
        DefaultLineMapper<Aluno> defaultLineMapper = new DefaultLineMapper<>();
        FixedLengthTokenizer lineTokenizer = new FixedLengthTokenizer();
        lineTokenizer.setNames("NOME", "MATRICULA", "CODIGO");
        lineTokenizer.setColumns(new Range(1, 41), new Range(42, 48), new Range(50, 55));

        BeanWrapperFieldSetMapper<Aluno> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Aluno.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
